package com.nftime.app.rederers;

import android.content.Context;
import android.opengl.GLSurfaceView;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.SimpleExoPlayer;

public abstract class GLWallpaperRenderer implements GLSurfaceView.Renderer {
    private static final String TAG = "GLWallpaperRenderer";
    final Context context;

    GLWallpaperRenderer(@NonNull final Context context) {
        this.context = context;
    }

    @NonNull
    protected Context getContext() {
        return context;
    }

    public abstract void setSourcePlayer(@NonNull final SimpleExoPlayer exoPlayer);
    public abstract void setScreenSize(int width, int height);
    public abstract void setVideoSizeAndRotation(int width, int height, int rotation);
    public abstract void setOffset(float xOffset, float yOffset);
}
