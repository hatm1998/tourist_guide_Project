package com.example.touristguide.Utilis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.R;

import java.util.List;

public class post_recycle_adapter extends RecyclerView.Adapter<post_recycle_adapter.ViewHolder>  {

    private List<Post> Post_List;
    private Context context;

    public post_recycle_adapter(List<Post> post_List)
    {
        this.Post_List = post_List;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_virtecal, parent, false);


        context = parent.getContext();

        return new post_recycle_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);

    }

    @Override
    public int getItemCount() {
        return Post_List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
