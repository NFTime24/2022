package com.nftime.app.RecyclerItem;

import java.io.Serializable;
import java.util.ArrayList;

public class CollectionItem implements Serializable {
    int resourceId;
    String title;
    String description;
    ArrayList collectionItems;

    public CollectionItem(int resourceId, String name, String description, ArrayList collectionItems) {
        this.resourceId = resourceId;
        this.title = name;
        this.description = description;
        this.collectionItems = collectionItems;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList getCollectionItems() {
        return collectionItems;
    }

    public void setCollectionItems(ArrayList collectionItems) {
        this.collectionItems = collectionItems;
    }
}
