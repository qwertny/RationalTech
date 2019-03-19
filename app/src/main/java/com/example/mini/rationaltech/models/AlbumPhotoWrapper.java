package com.example.mini.rationaltech.models;

import java.util.ArrayList;

/**
 * Represents one album with title and list of photos
 */

public class AlbumPhotoWrapper {
    private String Title;
    private ArrayList<Photo> photos = new ArrayList<>();

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }
}
