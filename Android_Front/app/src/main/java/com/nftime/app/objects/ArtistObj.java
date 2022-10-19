package com.nftime.app.objects;

public class ArtistObj {
    public int id;
    public int profile_id;
    public String name;
    public String address;
    public String path;

    public ArtistObj(int id, int profile_id, String name, String address, String path){
        this.id = id;
        this.profile_id = profile_id;
        this.name = name;
        this.address = address;
        this.path = path;
    }

    @Override
    public String toString() {
        return "ArtistObj{" +
                "id=" + id +
                ", profile_id=" + profile_id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
