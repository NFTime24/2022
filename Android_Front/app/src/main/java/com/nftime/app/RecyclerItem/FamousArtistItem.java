package com.nftime.app.RecyclerItem;

public class FamousArtistItem {
    int resourceId;
    int ranking;
    String name;

    public FamousArtistItem(int resourceId, int ranking, String name) {
        this.resourceId = resourceId;
        this.ranking = ranking;
        this.name = name;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
