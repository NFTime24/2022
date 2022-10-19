package com.nftime.app.objects;

public class UserObj {
    public int id;
    public int profile_id;
    public String address;
    public String nickname;
    public String path;

    public UserObj(int id, int profile_id, String address, String nickname, String path){
        this.id = id;
        this.profile_id = profile_id;
        this.address = address;
        this.nickname = nickname;
        this.path = path;
    }

    @Override
    public String toString() {
        return "UserObj{" +
                "id=" + id +
                ", profile_id=" + profile_id +
                ", address='" + address + '\'' +
                ", nickname='" + nickname + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
