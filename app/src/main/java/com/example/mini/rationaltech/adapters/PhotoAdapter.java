package com.example.mini.rationaltech.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mini.rationaltech.R;
import com.example.mini.rationaltech.models.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Photo adapter
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private List<Photo> photos = new ArrayList<>();
    private PhotoInterface listener;
    public Context context;

    /**
     * Constructor
     *
     * @param context
     * @param listener
     */
    public PhotoAdapter(Context context, PhotoInterface listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View photoItemView = inflater.inflate(R.layout.recycler_photo_item, null, false);
        final PhotoViewHolder photoViewHolder = new PhotoViewHolder(photoItemView);

        photoViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.ItemClicked(photos.get(photoViewHolder.getAdapterPosition()));
                }
            }
        });

        return photoViewHolder;
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {

        Photo photo = photos.get(position);

        //Binding data
        holder.tvTitle.setText(photo.getTitle());
        holder.tvUrl.setText(photo.getUrl());
        Picasso.with(context)
                .load(photo.getThumbnailUrl())
                .into(holder.ivThumbnail);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    /**
     * Refresh
     *
     * @param photos
     */
    public void refresh(List<Photo> photos) {
        this.photos.clear();
        this.photos.addAll(photos);
        notifyDataSetChanged();
    }


    /**
     * ViewHolder for photo item
     */
    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TextView tvUrl;
        public ImageView ivThumbnail;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvPhotoTitle);
            tvUrl = itemView.findViewById(R.id.tvPhotoUrl);
            ivThumbnail = itemView.findViewById(R.id.ivPhotoThumbnail);
        }
    }

    public interface PhotoInterface {
        void ItemClicked(Photo photo);
    }
}
