package com.nftime.app;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.klipwallet.app2app.api.Klip;
import com.nftime.app.R;
import com.nftime.app.actions.KlipAction;
import com.nftime.app.objects.NftWorkObj;
import com.nftime.app.services.LiveWallpaperService;
import com.nftime.app.util.ApplicationConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NftActivity extends AppCompatActivity {
    private static final String LOG_TAG = "test";

    private Context context;
    private Activity activity;

    private ImageView nftImageView;
    private Button settingBackgroundButton;
    private ProgressBar nftProgressBar;

    private TextView tvPrice;
    private TextView tvTitle;
    private TextView tvName;
    private ImageView ivProfile;
    private TextView tvCreator;
    private TextView tvInfoArtDetail;

    private String nftType;
    private NftWorkObj nft;

    private String nftPath;

    private File mediaFile;

    private ImageView favoriteIcon;
    private TextView favoriteCountTextView;
    private boolean i = false;
    private int favoriteCount = 99;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nft);

        context = this;
        activity = ((Activity) context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = activity.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
            flags = flags ^ View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // use XOR here for remove LIGHT_STATUS_BAR from flags
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
        }
        
        // 좋아요 기능
        favoriteIcon = findViewById(R.id.icon_favorite);
        favoriteCountTextView = findViewById(R.id.tv_favorite_count);
        favoriteCountTextView.setText(String.valueOf(favoriteCount));

        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i == true) {
                    favoriteIcon.setImageResource(R.drawable.ic_icon_favorite_border);
                    favoriteCount -= 1;
                    favoriteCountTextView.setText(String.valueOf(favoriteCount));
                    i = false;
                } else {
                    favoriteIcon.setImageResource(R.drawable.ic_icon_favorite);
                    favoriteCount += 1;
                    favoriteCountTextView.setText(String.valueOf(favoriteCount));
                    i = true;
                }
            }
        });
        //

        Intent intent = getIntent();

        nftType = intent.getStringExtra("nftType");

        nft = new NftWorkObj(
                intent.getIntExtra("work_id", 0),
                intent.getIntExtra("work_price", 0),
                intent.getIntExtra("filesize", 0),
                intent.getIntExtra("exhibition_id", 0),
                intent.getStringExtra("work_name"),
                intent.getStringExtra("description"),
                intent.getStringExtra("category"),
                intent.getStringExtra("filename"),
                intent.getStringExtra("filetype"),
                intent.getStringExtra("path"),
                intent.getStringExtra("thumbnail_path"),
                intent.getStringExtra("artist_name"),
                intent.getStringExtra("artist_profile_path"),
                intent.getStringExtra("artist_address")
        );
        Log.d("test", nft.toString());

        nftImageView = findViewById(R.id.nft_imageView);
        settingBackgroundButton = findViewById(R.id.setting_background_button);
        nftProgressBar = findViewById(R.id.nft_progressbar);
        tvInfoArtDetail = findViewById(R.id.nft_textView);

        if(MainActivity.klaytnAddress == null){
            settingBackgroundButton.setVisibility(View.INVISIBLE);
            nftProgressBar.setVisibility(View.INVISIBLE);
        }
        else if(MainActivity.ownedWorkIds.get(nft.work_id) != null && MainActivity.ownedWorkIds.get(nft.work_id)){
            settingBackgroundButton.setVisibility(View.VISIBLE);
            nftProgressBar.setVisibility(View.VISIBLE);

            settingBackgroundButton.setText("SET BACKGROUND");
            settingBackgroundButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(v.getContext());
                    try {
                        wallpaperManager.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // 파일 형식에 따라 다른 로직 실행
                    if(nftType.equals("image")){
                        setImageWallpaper();
                    }
                    else if(nftType.equals("video")){
                        setVideoWallpaper();
                    }
                }
            });

            ArrayList<Integer> nftIds = new ArrayList<>();
            Iterator<Integer> keys  = MainActivity.ownedNftIds2WorkIds.keySet().iterator();
            while(keys.hasNext()){
                int nftId = keys.next();
                int workId = MainActivity.ownedNftIds2WorkIds.get(nftId);
                if(workId == nft.work_id){
                    nftIds.add(nftId);
                }
            }

            StringBuilder nftIds_strBuilder = new StringBuilder("");
            nftIds.forEach((v) -> {
                nftIds_strBuilder.append(v);
                nftIds_strBuilder.append(" | ");
            });

            String nftIds_str = nftIds_strBuilder.toString();
/*            tvInfoArtDetail.setText(nftIds_str);*/
        }
        else{
            settingBackgroundButton.setVisibility(View.VISIBLE);
            nftProgressBar.setVisibility(View.INVISIBLE);

            settingBackgroundButton.setText("Purchase NFT");
            settingBackgroundButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), NFTPurchaseActivity.class);

                    intent.putExtra("work_id", nft.work_id);
                    intent.putExtra("work_price", nft.work_price);
                    intent.putExtra("filesize", nft.filesize);
                    intent.putExtra("exhibition_id", nft.exhibition_id);
                    intent.putExtra("work_name", nft.work_name);
                    intent.putExtra("description", nft.description);
                    intent.putExtra("category", nft.category);
                    intent.putExtra("filename", nft.filename);
                    intent.putExtra("filetype", nft.filetype);
                    intent.putExtra("path", nft.path);
                    intent.putExtra("thumbnail_path", nft.thumbnail_path);
                    intent.putExtra("artist_name", nft.artist_name);
                    intent.putExtra("artist_profile_path", nft.artist_profile_path);
                    intent.putExtra("artist_address", nft.artist_address);

                    view.getContext().startActivity(intent);
                }
            });
        }

        tvPrice = findViewById(R.id.tv_price);
        tvTitle = findViewById(R.id.tv_title);
        tvName = findViewById(R.id.tv_name);
        ivProfile = findViewById(R.id.iv_profile);
        tvCreator = findViewById(R.id.tv_creator);
        tvInfoArtDetail = findViewById(R.id.tv_info_art_detail);

        // tvPrice.setText(String.valueOf(nft.work_price));
        tvTitle.setText(nft.work_name);
        tvName.setText(nft.artist_name);
        Glide.with(context)
                .load(ApplicationConstants.AWS_URL + nft.artist_profile_path)
                .centerCrop()
                .placeholder(R.drawable.mypage_profile_picture)
                .into(ivProfile);

        tvCreator.setText(nft.artist_name);

        String description = nft.description;
        description = description.replace("\\n","\n");
        tvInfoArtDetail.setText(description);

        String thumbnail_url;

        if(nftType.equals("video")){
            thumbnail_url = ApplicationConstants.AWS_URL + nft.thumbnail_path;
        }
        else{
            thumbnail_url = ApplicationConstants.AWS_URL + nft.path;
            nftProgressBar.setVisibility(View.INVISIBLE);
        }

        nftPath = ApplicationConstants.AWS_URL + nft.path;

        /*Picasso.get()
                .load(thumbnail_url)
                .error(R.drawable.img_4840)
                .into(nftImageView);*/

        Glide.with(context)
                .load(thumbnail_url)
                .centerCrop()
                .placeholder(R.drawable.app_icon_nftime)
                .into(nftImageView);
    }

    // NFT가 단순 이미지면 바로 LiveWallpaperService 실행 > services/LiveWallpaperService.class
    private void setImageWallpaper(){
        // SharedPreferences로 NFT type(image)과 uri를 넘김
        SharedPreferences sharedPreferences = getSharedPreferences(ApplicationConstants.WALLPAPER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(ApplicationConstants.NFT_TYPE_PREF, nftType);
        editor.putString(ApplicationConstants.NFT_URI_PREF, nftPath);
        editor.apply();

        //LiveWallpaperService 실행
        Intent intent = new Intent(
                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(context, LiveWallpaperService.class));
        startActivity(intent);
    }

    // NFT가 video 파일이면 버튼을 비활성화하고 Video Downloader 실행
    private void setVideoWallpaper(){
        settingBackgroundButton.setText("NFT Wallpaper Applying");
        settingBackgroundButton.setEnabled(false);

        VideoDownloader videoDownloader = new VideoDownloader(context);
        videoDownloader.execute();
    }

    class VideoDownloader extends AsyncTask<Void, Long, Boolean> {
        Context mContext;

        public VideoDownloader(Context context){
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // OkHttp를 이용하여 video 파일을 다운로드 받음.
            OkHttpClient client = new OkHttpClient();
            String url = nftPath;
            Call call = client.newCall(new Request.Builder().url(url).get().build());

            try {
                Response response = call.execute();
                if (response.code() == 200 || response.code() == 201) {

                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        Log.d(LOG_TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    InputStream inputStream = null;
                    try {
                        inputStream = response.body().byteStream();

                        byte[] buff = new byte[1024 * 4];
                        long downloaded = 0;
                        long target = response.body().contentLength();
                        // mediaFile 변수에 파일을 다운로드 받음. getCacheDir을 사용했으므로 internal storage임.
                        mediaFile = new File(mContext.getCacheDir(), "liveWallpaper.mp4");
                        OutputStream output = new FileOutputStream(mediaFile);

                        // publishProgress는 onProgressUpdate 함수를 실행시킴. 파일 다운로드 과정 값을 가져올 수 있음.
                        // 0으로 시작해서
                        publishProgress(0L, target);
                        while (true) {
                            int readed = inputStream.read(buff);

                            if (readed == -1) {
                                break;
                            }
                            output.write(buff, 0, readed);
                            //write buff
                            downloaded += readed;
                            // 다운받은 파일량을 계속 보내줌.
                            publishProgress(downloaded, target);
                            if (isCancelled()) {
                                return false;
                            }
                        }

                        output.flush();
                        output.close();

                        // 파일 크기랑 받아진 파일량이랑 비교해서 파일 다 받아졌는지 확인 > onPostExecute 실행!
                        return downloaded == target;
                    } catch (IOException ignore) {
                        return false;
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    }
                } else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        // 위에서 계속 보낸 값으로 progress bar 업데이트
        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
            nftProgressBar.setMax(values[1].intValue());
            nftProgressBar.setProgress(values[0].intValue());

        }

        // doInBackground 끝나면 실행
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            // 파일 받아졌는지 확인하고 받아졌으면 LiveWallpaperService.class 실행
            if (mediaFile != null && mediaFile.exists() && aBoolean) {
                // 다 됐으니까 progress bar 지우고, background button text 변경
                nftProgressBar.setVisibility(View.GONE);
                settingBackgroundButton.setText("NFT WallPaper 적용 완료!");

                SharedPreferences sharedPreferences = getSharedPreferences(ApplicationConstants.WALLPAPER_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(ApplicationConstants.NFT_TYPE_PREF, nftType);
                // Uri로 받으 파일의 path값을 보냄.
                editor.putString(ApplicationConstants.NFT_URI_PREF, mediaFile.getAbsolutePath());
                editor.apply();

                Intent wallpaperIntent = new Intent(
                        WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                wallpaperIntent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                        new ComponentName(mContext, LiveWallpaperService.class));
                startActivity(wallpaperIntent);
            }
            else {
                settingBackgroundButton.setText("Download Failed");
            }
        }

    }
}