package com.example.touristguide.video_player;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.touristguide.R;
import com.example.touristguide.video_player.models.Post;
import com.google.common.base.MoreObjects;

import java.util.ArrayList;


public class VideoPlayerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Post> posts;
    private RequestManager requestManager;


    public VideoPlayerRecyclerAdapter(ArrayList<Post> posts, RequestManager requestManager) {
        this.posts = posts;
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new VideoPlayerViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_virtecal, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        viewHolder.setIsRecyclable(true);
        ((VideoPlayerViewHolder)viewHolder).onBind(posts.get(i), requestManager);
    }
    
    @Override
    public int getItemCount() {
        return posts.size();
    }

}














