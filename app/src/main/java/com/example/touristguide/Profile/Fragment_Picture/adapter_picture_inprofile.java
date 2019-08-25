package com.example.touristguide.Profile.Fragment_Picture;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.touristguide.R;
import com.example.touristguide.Utilis.VideoRequestHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_picture_inprofile extends RecyclerView.Adapter<adapter_picture_inprofile.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView IMG_post, PlayerPost;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            IMG_post = itemView.findViewById(R.id.Img_inprofile);
            progressBar = itemView.findViewById(R.id.progress_pic_profile);
            PlayerPost = itemView.findViewById(R.id.img_player_icon);

        }


    }

    private Context context;
    private ArrayList<setPicture> list;

    public adapter_picture_inprofile(Context context, ArrayList<setPicture> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.style_picture_inprofile, parent, false);
        return new ViewHolder(v);
    }

    private RequestManager initGlide() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background);

        return Glide.with(context)
                .setDefaultRequestOptions(options);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final setPicture pos = list.get(position);

        VideoRequestHandler videoRequestHandler;
        Picasso picassoInstance;

        videoRequestHandler = new VideoRequestHandler();
        picassoInstance = new Picasso.Builder(context.getApplicationContext())
                .addRequestHandler(videoRequestHandler)
                .build();

        Log.d("Media", pos.getPicture());
        if (!pos.getPicture().contains(".mp4")) {
            holder.PlayerPost.setVisibility(View.GONE);
            picassoInstance.load(pos.getPicture()).into(holder.IMG_post, new Callback() {
                @Override
                public void onSuccess() {
                    holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        } else {

            initGlide().load(pos.getPicture()).into(holder.IMG_post);
            holder.progressBar.setVisibility(View.GONE);

//            requestManager.load(pos.getPicture()).into(holder.IMG_post);
//            //Picasso.load(ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, pos.getPicture())).resize(px, px).centerCrop().into(imageview);
        }
//        Picasso.get().load(pos.getPicture()).into(holder.IMG_post,new Callback() {
//            @Override
//            public void onSuccess() {
//                holder.progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onError(Exception e) {
//
//            }
//
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
