package com.example.mini.rationaltech.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mini.rationaltech.R;
import com.example.mini.rationaltech.models.AlbumPhotoWrapper;
import com.example.mini.rationaltech.models.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxx on 18/03/2019.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {
    private List<AlbumPhotoWrapper> albums = new ArrayList<>();
    private AlbumInterface listener;
    public Context context;
    private PhotoAdapter photoAdapter;

    /**
     * Constructor
     *
     * @param context
     * @param listener
     */
    public AlbumAdapter(Context context, AlbumInterface listener) {
        this.context = context;
        this.listener = listener;

    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View albumItemView = inflater.inflate(R.layout.recycler_album_item, null, false);
        final AlbumViewHolder albumViewHolder = new AlbumViewHolder(albumItemView);

        albumViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.ItemClicked(albums.get(albumViewHolder.getAdapterPosition()));
                }
            }
        });

        return albumViewHolder;
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {

        AlbumPhotoWrapper album = albums.get(position);

        //Binding data
        holder.tvTitle.setText(album.getTitle());
        photoAdapter.refresh(album.getPhotos());

    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    /**
     * Refresh
     *
     * @param albums
     */
    public void refresh(List<AlbumPhotoWrapper> albums) {
        this.albums.clear();
        this.albums.addAll(albums);
        notifyDataSetChanged();
    }

    /**
     * ViewHolder for album item
     */
    public class AlbumViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public RecyclerView rvAlbumPhotos;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            rvAlbumPhotos = itemView.findViewById(R.id.rvAlbumPhotos);
            photoAdapter = new PhotoAdapter(context, new PhotoAdapter.PhotoInterface() {
                @Override
                public void ItemClicked(Photo photo) {
                    //Showing image externally using browser
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(photo.getUrl()));
                    context.startActivity(browserIntent);
                }
            });
            rvAlbumPhotos.setAdapter(photoAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            rvAlbumPhotos.setLayoutManager(layoutManager);
            tvTitle = itemView.findViewById(R.id.tvAlbumTitle);
        }
    }

    public interface AlbumInterface {
        void ItemClicked(AlbumPhotoWrapper album);
    }
}
