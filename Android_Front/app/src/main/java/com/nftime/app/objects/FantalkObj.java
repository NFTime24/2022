package com.nftime.app.objects;

import java.util.Date;

public class FantalkObj {
    public int post_id;
    public int artist_id;
    public int owner_id;
    public int like_count;
    public String post_text;
    public String nickname;
    public String path;
    public String create_time;
    public String modify_time;

    public FantalkObj(
            int post_id,
            int artist_id,
            int owner_id,
            int like_count,
            String post_text,
            String nickname,
            String path,
            String create_time,
            String modify_time
    ) {
        this.post_id = post_id;
        this.artist_id = artist_id;
        this.owner_id = owner_id;
        this.like_count = like_count;
        this.post_text = post_text;
        this.nickname = nickname;
        this.path = path;
        this.create_time = create_time;
        this.modify_time = modify_time;
    }

    @Override
    public String toString() {
        return "FantalkObj{" +
                "post_id=" + post_id +
                ", artist_id=" + artist_id +
                ", owner_id=" + owner_id +
                ", like_count=" + like_count +
                ", post_text='" + post_text + '\'' +
                ", nickname='" + nickname + '\'' +
                ", path='" + path + '\'' +
                ", create_time=" + create_time + '\'' +
                ", modify_time=" + modify_time +
                '}';
    }
}
