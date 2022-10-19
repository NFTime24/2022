package com.nftime.app.RecyclerItem;

public class MyPageListItem {
    int itemIcon;
    String itemText;

    public MyPageListItem(int fanprofile, String fanname) {
        this.itemIcon = fanprofile;
        this.itemText = fanname;
    }

    public int getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(int itemIcon) {
        this.itemIcon = itemIcon;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }
}
