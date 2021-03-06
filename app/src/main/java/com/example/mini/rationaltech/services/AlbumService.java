package com.example.mini.rationaltech.services;

import com.example.mini.rationaltech.utilities.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AlbumService {

    /**
     * Request that gets list of albums
     *
     * @return
     */
    @GET(Constants.BASE_URL + "albums")
    Call<ArrayList<Object>> getAlbums();
}
