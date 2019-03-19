package com.example.mini.rationaltech.models;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxx on 19/03/2019.
 */

public class AlbumViewModel extends ViewModel {
    public MutableLiveData<List<AlbumPhotoWrapper>> albums;



}
