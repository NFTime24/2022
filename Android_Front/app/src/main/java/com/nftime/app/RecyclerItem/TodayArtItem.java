package com.nftime.app.RecyclerItem;

public class TodayArtItem {
    int resourceId;
    String title;
    String artist;

    public TodayArtItem(int resourceId, String title, String artist) {
        this.resourceId = resourceId;
        this.title = title;
        this.artist = artist;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
