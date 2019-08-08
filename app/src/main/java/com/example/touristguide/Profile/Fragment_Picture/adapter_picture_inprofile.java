package com.example.touristguide.Profile.Fragment_Picture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_picture_inprofile  extends RecyclerView.Adapter<adapter_picture_inprofile.ViewHolder> {


    public  static class ViewHolder extends RecyclerView.ViewHolder{
          private ImageView IMG_post;
          private ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            IMG_post=itemView.findViewById(R.id.Img_inprofile);
            progressBar=itemView.findViewById(R.id.progress_pic_profile);
        }


    }

    private Context context;
    private ArrayList<setPicture> list;
    public adapter_picture_inprofile(Context context , ArrayList<setPicture> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.style_picture_inprofile,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final  ViewHolder holder, int position) {
        final setPicture pos=list.get(position);
        Picasso.get().load(pos.getPicture()).into(holder.IMG_post,new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
