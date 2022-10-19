package com.nftime.app.objects;

public class NftWorkObj {
    public int work_id;
    public int work_price;
    public int filesize;
    public int exhibition_id;
    public String work_name;
    public String description;
    public String category;
    public String filename;
    public String filetype;
    public String path;
    public String thumbnail_path;
    public String artist_name;
    public String artist_profile_path;
    public String artist_address;

    public NftWorkObj(int work_id,
                      int work_price,
                      int filesize,
                      int exhibition_id,
                      String work_name,
                      String description,
                      String category,
                      String filename,
                      String filetype,
                      String path,
                      String thumbnail_path,
                      String artist_name,
                      String artist_profile_path,
                      String artist_address){
        this.work_id = work_id;
        this.work_price = work_price;
        this.filesize = filesize;
        this.exhibition_id = exhibition_id;
        this.work_name = work_name;
        this.description = description;
        this.category = category;
        this.filename = filename;
        this.filetype = filetype;
        this.path = path;
        this.thumbnail_path = thumbnail_path;
        this.artist_name = artist_name;
        this.artist_profile_path = artist_profile_path;
        this.artist_address = artist_address;
    }

    @Override
    public String toString() {
        return "NftWorkObj{" +
                "work_id=" + work_id +
                ", work_price=" + work_price +
                ", filesize=" + filesize +
                ", work_name='" + work_name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", filename='" + filename + '\'' +
                ", filetype='" + filetype + '\'' +
                ", path='" + path + '\'' +
                ", thumbnail_path='" + thumbnail_path + '\'' +
                ", artist_name='" + artist_name + '\'' +
                ", artist_profile_path='" + artist_profile_path + '\'' +
                ", artist_address='" + artist_address + '\'' +
                '}';
    }
}
