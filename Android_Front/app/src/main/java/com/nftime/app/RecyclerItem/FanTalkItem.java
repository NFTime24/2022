package com.nftime.app.RecyclerItem;

public class FanTalkItem {
    int fanprofile;
    String fanname;
    String fantalk;

    public FanTalkItem(int fanprofile, String fanname, String fantalk) {
        this.fanprofile = fanprofile;
        this.fanname = fanname;
        this.fantalk = fantalk;
    }

    public int getFanprofile() {
        return fanprofile;
    }

    public void setFanprofile(int fanprofile) {
        this.fanprofile = fanprofile;
    }

    public String getFanname() {
        return fanname;
    }

    public void setFanname(String fanname) {
        this.fanname = fanname;
    }

    public String getFantalk() {
        return fantalk;
    }

    public void setFantalk(String fantalk) {
        this.fantalk = fantalk;
    }
}
