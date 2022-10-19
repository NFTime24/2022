package com.nftime.app.RecyclerItem;

import java.io.Serializable;

public class ArtItem implements Serializable {
    String title;
    String name;
    int resourceId;
    int price;

    public ArtItem(int resourceId, String title, String name, int price) {
        this.title = title;
        this.name = name;
        this.resourceId = resourceId;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
