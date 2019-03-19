package com.example.mini.rationaltech.services;

import com.example.mini.rationaltech.utilities.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PhotoService {

    /**
     * Request that gets list of photos
     *
     * @return
     */
    @GET(Constants.BASE_URL + "photos")
    Call<ArrayList<Object>> getPhotos();
}
