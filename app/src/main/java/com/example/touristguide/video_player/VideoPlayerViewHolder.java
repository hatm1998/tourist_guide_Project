package com.example.touristguide.video_player;


import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.touristguide.R;
import com.example.touristguide.video_player.models.Post;


public class VideoPlayerViewHolder extends RecyclerView.ViewHolder {

    FrameLayout media_container;
    ImageView post_image_View, volumeControl;
    ProgressBar progressBar;
    View parent;
    RequestManager requestManager;

    public VideoPlayerViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        media_container = itemView.findViewById(R.id.media_container_post);
        post_image_View = itemView.findViewById(R.id.post_image_View);
        progressBar = itemView.findViewById(R.id.progressBarPost);
        volumeControl = itemView.findViewById(R.id.volume_control);
    }

    public void onBind(Post post, RequestManager requestManager) {
        this.requestManager = requestManager;
        parent.setTag(this);
        this.requestManager
                .load(post.getMedia_url())
                .into(post_image_View);
    }


}














