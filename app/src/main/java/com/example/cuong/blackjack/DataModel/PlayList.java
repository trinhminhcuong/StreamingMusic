package com.example.cuong.blackjack.DataModel;

/**
 * Created by Cuong on 5/15/2018.
 */

public class PlayList {

    private String id;
    private String name;

    public PlayList(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
