package com.example.touristguide.Commnets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.R;

import java.util.ArrayList;

public class Adapter_Comments  extends RecyclerView.Adapter<Adapter_Comments.ViewHolder> {


    public  static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }


    }

    private Context context;
    private ArrayList<set_Comment> list;
    public Adapter_Comments( Context context, ArrayList<set_Comment> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( this.context).inflate(R.layout.style_comments,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final set_Comment pos=list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
