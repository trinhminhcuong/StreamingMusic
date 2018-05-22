package com.example.cuong.blackjack.DataModel;

/**
 * Created by Cuong on 5/15/2018.
 */

public class Song {
    private  String name;
    private String singer;
    private String writter;
    private String type;
    private String listens;
    private String url;
    private String lyrics;

    public Song(String name, String singer) {
        this.name = name;
        this.singer = singer;
    }

    public Song(String name, String singer, String writter, String type, String listens, String url,String lyrics) {
        this.name = name;
        this.singer = singer;
        this.writter = writter;
        this.type = type;
        this.listens = listens;
        this.url = url;
        this.lyrics=lyrics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getWritter() {
        return writter;
    }

    public void setWritter(String writter) {
        this.writter = writter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getListens() {
        return listens;
    }

    public void setListens(String listens) {
        this.listens = listens;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
