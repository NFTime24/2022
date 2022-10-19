package com.nftime.app.RecyclerItem;

public class ArtistItem {
    String artistModifier;
    String artistName;
    String artName;
    int art;
    int profile;

    public ArtistItem(String artistModifier, String artistName, String artName, int profile, int art) {
        this.artistModifier = artistModifier;
        this.artistName = artistName;
        this.artName = artName;
        this.profile = profile;
        this.art = art;
    }

    public String getArtistModifier() {
        return artistModifier;
    }

    public void setArtistModifier(String artistModifier) {
        this.artistModifier = artistModifier;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtName() {
        return artName;
    }

    public void setArtName(String artName) {
        this.artName = artName;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public int getArt() {
        return art;
    }

    public void setArt(int art) {
        this.art = art;
    }
}
