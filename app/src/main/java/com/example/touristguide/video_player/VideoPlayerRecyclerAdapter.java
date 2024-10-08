package com.example.touristguide.video_player;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.touristguide.R;
import com.example.touristguide.video_player.models.Post;

import java.util.ArrayList;


public class VideoPlayerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Post> posts;
    private RequestManager requestManager;
    private Context context;


    public VideoPlayerRecyclerAdapter(Context context ,ArrayList<Post> posts, RequestManager requestManager) {
        this.posts = posts;
        this.requestManager = requestManager;
        this.context=context;
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
        ((VideoPlayerViewHolder)viewHolder).onBind(context,posts.get(i), requestManager);
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

}
