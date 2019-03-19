package com.example.mini.rationaltech.activities;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mini.rationaltech.R;
import com.example.mini.rationaltech.adapters.AlbumAdapter;
import com.example.mini.rationaltech.models.Album;
import com.example.mini.rationaltech.models.AlbumPhotoWrapper;
import com.example.mini.rationaltech.models.Photo;
import com.example.mini.rationaltech.services.AlbumService;
import com.example.mini.rationaltech.services.PhotoService;
import com.example.mini.rationaltech.utilities.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private PhotoService photoService;
    private AlbumService albumService;
    private ArrayList<Photo> photos = new ArrayList<>();
    private ArrayList<Album> albums = new ArrayList<>();
    private ArrayList<AlbumPhotoWrapper> data = new ArrayList<>();
    private RecyclerView rvPhotos;
    private AlbumAdapter albumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoService = RetrofitClient.getInstance().create(PhotoService.class);
        albumService = RetrofitClient.getInstance().create(AlbumService.class);


        rvPhotos = findViewById(R.id.rvPhotos);
        albumAdapter = new AlbumAdapter(this, new AlbumAdapter.AlbumInterface() {
            @Override
            public void ItemClicked(AlbumPhotoWrapper album) {

            }
        });
        rvPhotos.setAdapter(albumAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvPhotos.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = 8;
                outRect.bottom = 8;
            }
        };
        rvPhotos.addItemDecoration(itemDecoration);

        makeGetAlbumsRequest();
    }

    private void makeGetAlbumsRequest() {

        Call<ArrayList<Object>> getAlbums = albumService.getAlbums();
        getAlbums.enqueue(new Callback<ArrayList<Object>>() {
            @Override
            public void onResponse(Call<ArrayList<Object>> call, final Response<ArrayList<Object>> response) {
                if (response.body() != null) {
                    //read each object of array with Json library
                    JSONArray jsonArray = new JSONArray(response.body());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        //get the object
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Album album = new Album();
                            album.setId(jsonObject.getInt("id"));
                            album.setUserId(jsonObject.getInt("userId"));
                            album.setTitle(jsonObject.getString("title"));

                            albums.add(album);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    makeGetPhotosRequest();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Object>> call, Throwable t) {
            }
        });
    }

    private void makeGetPhotosRequest() {

        final Call<ArrayList<Object>> getPhotos = photoService.getPhotos();
        getPhotos.enqueue(new Callback<ArrayList<Object>>() {
            @Override
            public void onResponse(Call<ArrayList<Object>> call, final Response<ArrayList<Object>> response) {
                if (response.body() != null) {
                    //read each object of array with Json library
                    JSONArray jsonArray = new JSONArray(response.body());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        //get the object
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Photo photo = new Photo();
                            photo.setAlbumId(jsonObject.getInt("albumId"));
                            photo.setId(jsonObject.getInt("id"));
                            photo.setTitle(jsonObject.getString("title"));
                            photo.setUrl(jsonObject.getString("url"));
                            photo.setThumbnailUrl(jsonObject.getString("thumbnailUrl"));
                            photos.add(photo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    organizePhotos();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Object>> call, Throwable t) {
            }
        });
    }

    /**
     * Puts photo into proper album
     */
    private void organizePhotos() {
        for (Album album : albums) {
            AlbumPhotoWrapper albumPhotoWrapper = new AlbumPhotoWrapper();
            for (Photo photo : photos) {
                if (photo.getAlbumId() == album.getId()) {
                    albumPhotoWrapper.setTitle(album.getTitle());
                    ArrayList<Photo> list = albumPhotoWrapper.getPhotos();
                    list.add(photo);
                    albumPhotoWrapper.setPhotos(list);
                }
            }
            data.add(albumPhotoWrapper);
        }
        albumAdapter.refresh(data);
    }
}
