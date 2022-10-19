package com.nftime.app.services;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.nftime.app.R;
import com.nftime.app.rederers.GLES20WallpaperRenderer;
import com.nftime.app.rederers.GLES30WallpaperRenderer;
import com.nftime.app.rederers.GLWallpaperRenderer;
import com.nftime.app.rederers.Utils;
import com.nftime.app.util.ApplicationConstants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import wseemann.media.FFmpegMediaMetadataRetriever;

public class LiveWallpaperService extends WallpaperService {
    private Engine wallpaperEngine;

    // Service 시작하면 Engine 실행
    @Override
    public Engine onCreateEngine() {
        // SharedPreferences에서 NFT type이랑 Uri를 가져옴
        SharedPreferences sharedPreferences = getSharedPreferences(ApplicationConstants.WALLPAPER_PREFERENCES, Context.MODE_PRIVATE);
        String nftType = sharedPreferences.getString(ApplicationConstants.NFT_TYPE_PREF, null);
        String nftUri = sharedPreferences.getString(ApplicationConstants.NFT_URI_PREF, null);
        Log.d("test", nftType);

        // NFT 타입에 따라 사용하는 엔진을 적절하게 선택해 줌.
        if(nftType.equals("image")){
            // Uri는 생성자로 전달해 줌.
            wallpaperEngine = new ImageWallpaperEngine(nftUri);
        } else if (nftType.equals("video")){
            wallpaperEngine = new VideoWallpaperEngine(this, nftUri);
        } else if (nftType.equals("3d")) {

        }

        return wallpaperEngine;
    }

    private class VideoWallpaperEngine extends Engine {
        private String vidUri;

        private static final String TAG = "GLWallpaperEngine";
        private final Context context;
        private GLWallpaperSurfaceView glSurfaceView = null;
        private SimpleExoPlayer exoPlayer = null;
        private MediaSource videoSource = null;
        private DefaultTrackSelector trackSelector = null;
        private GLWallpaperRenderer renderer = null;
        private boolean allowSlide = false;
        private int videoRotation = 0;
        private int videoWidth = 0;
        private int videoHeight = 0;
        private long progress = 0;

        private class GLWallpaperSurfaceView extends GLSurfaceView {
            @SuppressWarnings("unused")
            private static final String TAG = "GLWallpaperSurface";

            public GLWallpaperSurfaceView(Context context) {
                super(context);
            }

            /**
             * This is a hack. Because Android Live Wallpaper only has a Surface.
             * So we create a GLSurfaceView, and when drawing to its Surface,
             * we replace it with WallpaperEngine's Surface.
             */
            @Override
            public SurfaceHolder getHolder() {
                return getSurfaceHolder();
            }

            void onDestroy() {
                super.onDetachedFromWindow();
            }
        }

        public VideoWallpaperEngine(@NonNull final Context context, String _vidUri){
            this.context = context;
            setTouchEventsEnabled(false);

            vidUri = _vidUri;
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder surfaceHolder) {
            super.onSurfaceCreated(surfaceHolder);
            createGLSurfaceView();
            int width = surfaceHolder.getSurfaceFrame().width();
            int height = surfaceHolder.getSurfaceFrame().height();
            renderer.setScreenSize(width, height);
            startPlayer();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (renderer != null) {
                if (visible) {
                    glSurfaceView.onResume();
                    startPlayer();
                } else {
                    stopPlayer();
                    glSurfaceView.onPause();
                    // Prevent useless renderer calculating.
                    allowSlide = false;
                }
            }
        }

        @Override
        public void onOffsetsChanged(
                float xOffset, float yOffset,
                float xOffsetStep, float yOffsetStep,
                int xPixelOffset, int yPixelOffset
        ) {
            super.onOffsetsChanged(
                    xOffset, yOffset, xOffsetStep,
                    yOffsetStep, xPixelOffset, yPixelOffset
            );
            if (allowSlide && !isPreview()) {
                renderer.setOffset(0.5f - xOffset, 0.5f - yOffset);
            }
        }

        @Override
        public void onSurfaceChanged(
                SurfaceHolder surfaceHolder, int format,
                int width, int height
        ) {
            super.onSurfaceChanged(surfaceHolder, format, width, height);
            renderer.setScreenSize(width, height);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            stopPlayer();
            glSurfaceView.onDestroy();
        }

        private void createGLSurfaceView() {
            if (glSurfaceView != null){
                glSurfaceView.onDestroy();
                glSurfaceView = null;
            }
            glSurfaceView = new GLWallpaperSurfaceView(context);
            final ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
            if(activityManager == null)
                throw new RuntimeException("Cannot get ActivityManager");
            final ConfigurationInfo configInfo = activityManager.getDeviceConfigurationInfo();
            if (configInfo.reqGlEsVersion >= 0x30000) {
                Utils.debug(TAG, "Support GLESv3");
                glSurfaceView.setEGLContextClientVersion(3);
                renderer = new GLES30WallpaperRenderer(context);
            } else if (configInfo.reqGlEsVersion >= 0x20000) {
                Utils.debug(TAG, "Fallback to GLESv2");
                glSurfaceView.setEGLContextClientVersion(2);
                renderer = new GLES20WallpaperRenderer(context);
            } else {
                Toast.makeText(context, R.string.gles_version, Toast.LENGTH_LONG).show();
                throw new RuntimeException("Needs GLESv2 or higher");
            }
            glSurfaceView.setPreserveEGLContextOnPause(true);
            glSurfaceView.setRenderer(renderer);
            // On demand render will lead to black screen.
            glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }

        private void getVideoMetadata() throws IOException {
            /*final MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            final AssetFileDescriptor afd = getAssets().openFd(vidUri);
            mmr.setDataSource(
                    afd.getFileDescriptor(),
                    afd.getStartOffset(),
                    afd.getDeclaredLength()
            );
            afd.close();
            final String rotation = mmr.extractMetadata(
                    MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION
            );
            final String width = mmr.extractMetadata(
                    MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH
            );
            final String height = mmr.extractMetadata(
                    MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT
            );
            mmr.release();
            videoRotation = Integer.parseInt(rotation);
            videoWidth = Integer.parseInt(width);
            videoHeight = Integer.parseInt(height);*/

            final FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
            mmr.setDataSource(vidUri);
            final String rotation = mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
            final String width = mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            final String height = mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            mmr.release();
            videoRotation = Integer.parseInt(rotation);
            videoWidth = Integer.parseInt(width);
            videoHeight = Integer.parseInt(height);
        }

        private void startPlayer() {
            if (exoPlayer != null) {
                stopPlayer();
            }
            Utils.debug(TAG, "Player starting");

            try {
                getVideoMetadata();
            } catch (IOException e) {
                e.printStackTrace();
                // gg
                return;
            }
            trackSelector = new DefaultTrackSelector();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            exoPlayer.setVolume(0.0f);
            // Disable audio decoder.
            final int count = exoPlayer.getRendererCount();
            for (int i = 0; i < count; ++i) {
                if (exoPlayer.getRendererType(i) == C.TRACK_TYPE_AUDIO) {
                    trackSelector.setParameters(
                            trackSelector.buildUponParameters().setRendererDisabled(i, true)
                    );
                }
            }
            exoPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
            final DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                    context, Util.getUserAgent(context, "xyz.alynx.livewallpaper")
            );
            // ExoPlayer can load file:///android_asset/ uri correctly.
            videoSource = new ExtractorMediaSource.Factory(
                    dataSourceFactory
            ).createMediaSource(Uri.parse(vidUri));
            // Let we assume video has correct info in metadata, or user should fix it.
            renderer.setVideoSizeAndRotation(videoWidth, videoHeight, videoRotation);
            // This must be set after getting video info.
            renderer.setSourcePlayer(exoPlayer);
            exoPlayer.prepare(videoSource);
            // ExoPlayer's video size changed listener is buggy. Don't use it.
            // It give's width and height after rotation, but did not rotate frames.
            exoPlayer.seekTo(progress);
            exoPlayer.setPlayWhenReady(true);
        }

        private void stopPlayer() {
            if (exoPlayer != null) {
                if (exoPlayer.getPlayWhenReady()) {
                    Utils.debug(TAG, "Player stopping");
                    exoPlayer.setPlayWhenReady(false);
                    progress = exoPlayer.getCurrentPosition();
                    exoPlayer.stop();
                }
                exoPlayer.release();
                exoPlayer = null;
            }
            videoSource = null;
            trackSelector = null;
        }
    }

    // 파일이 Video 일때,
    private class VideoWallpaperEngine_old extends Engine {
        private MediaPlayer mediaPlayer;
        private String vidUri;

        public VideoWallpaperEngine_old(String _vidUri){
            vidUri = _vidUri;
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }

        // MediaPlayer를 사용해서 배경 Surface에 영상을 넣어 줌.
        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setSurface(holder.getSurface());
            try {
                mediaPlayer.reset();
                mediaPlayer.setVolume(0,0);
                // 파일 path Uri로 배경화면 설정
                Uri uri = Uri.parse(vidUri);
                mediaPlayer.setDataSource(getApplicationContext(), uri);
                mediaPlayer.setLooping(true);
                mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            if (visible) {
                mediaPlayer.start();
            } else {
                mediaPlayer.pause();
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            if (mediaPlayer.isPlaying()) mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (mediaPlayer != null) mediaPlayer.release();
        }
    }

    // Image 파일일 때, 코드가 많긴 한데, 그냥 draw() 함수가 실행된다고 보면 편합니다!
    private class ImageWallpaperEngine extends Engine {
        private final Handler handler = new Handler();
        private final Runnable drawRunner = () -> draw();
        private String imgUri;
        private Canvas canvas;
        private int width;
        private int height;
        private boolean visible = true;

        public ImageWallpaperEngine(String _imgUri) {
            imgUri = _imgUri;

            handler.post(drawRunner);
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible) {
                handler.post(drawRunner);
            } else {
                handler.removeCallbacks(drawRunner);
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.visible = false;
            handler.removeCallbacks(drawRunner);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            this.width = width;
            this.height = height;
            super.onSurfaceChanged(holder, format, width, height);

            Log.d("test", "onSurfaceChanged");
        }

        private void draw() {
            SurfaceHolder holder = getSurfaceHolder();

            try {
                canvas = holder.lockCanvas();
                if(canvas != null) {
                    Log.d("test", "Before Picasso");
                    // Picasso library를 이용하여 img를 서버에서 가져와 배경으로 설정
                    Picasso.get().load(imgUri).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            Log.d("test", bitmap.toString());
                            Log.d("test", "(bw, bh) = " + bitmap.getWidth() + "," + bitmap.getHeight());
                            Log.d("test", "(w, h) = " + width + "," + height);
                            int bw = bitmap.getWidth();
                            int bh = bitmap.getHeight();
                            Rect srcRectForRender;
                            Rect dstRectForRender;

                            if(bw < bh){
                                int bh2 = (bw * height)/width;
                                bh2 /= 2;
                                int y = bh/2;
                                srcRectForRender = new Rect( 0, y - bh2, bw, y + bh2);
                                dstRectForRender = new Rect( 0, 0, width, height );
                            }
                            else {
                                int bw2 = (bh * width)/height;
                                bw2 /= 2;
                                int x = bw/2;
                                srcRectForRender = new Rect( x - bw2, 0, x + bw2, bh);
                                dstRectForRender = new Rect( 0, 0, width, height );
                            }

                            canvas.drawBitmap ( bitmap, srcRectForRender, dstRectForRender, null );
                            holder.unlockCanvasAndPost(canvas);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            draw();
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });


                }
            } finally {
                /*if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);*/
            }
            handler.removeCallbacks(drawRunner);
        }
    }
}