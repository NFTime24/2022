package com.nftime.app.objects;

public class ExhibitionObj {
    public int exhibition_id;
    public String exhibition_name;
    public String exhibition_description;
    public String link;
    public String start_date;
    public String end_date;
    public String filename;
    public String filetype;
    public String path;

    public ExhibitionObj(int exhibition_id,
                          String exhibition_name,
                          String exhibition_description,
                          String link,
                          String start_date,
                          String end_date,
                          String filename,
                          String filetype,
                          String path) {
        this.exhibition_id = exhibition_id;
        this.exhibition_name = exhibition_name;
        this.exhibition_description = exhibition_description;
        this.link = link;
        this.start_date = start_date;
        this.end_date = end_date;
        this.filename = filename;
        this.filetype = filetype;
        this.path = path;
    }

    @Override
    public String toString() {
        return "ExhibitionsObj{" +
                "exibition_id=" + exhibition_id +
                ", exibition_name='" + exhibition_name + '\'' +
                ", exibition_description='" + exhibition_description + '\'' +
                ", link='" + link + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", filename='" + filename + '\'' +
                ", filetype='" + filetype + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
