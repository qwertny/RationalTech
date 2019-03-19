package com.example.mini.rationaltech.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Instance of this class represents one album provided by API;
 */
public class Album implements Serializable {
    @SerializedName("userId")
    @Expose
    private int userId;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    private ArrayList<Photo> albumPhotos = new ArrayList<>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Photo> getAlbumPhotos() {
        return albumPhotos;
    }

    public void setAlbumPhotos(ArrayList<Photo> albumPhotos) {
        this.albumPhotos = albumPhotos;
    }
}
