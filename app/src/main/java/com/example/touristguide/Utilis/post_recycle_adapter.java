package com.example.touristguide.Utilis;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class post_recycle_adapter extends RecyclerView.Adapter<post_recycle_adapter.ViewHolder> {

    private List<Post> Post_List;
    private Context context;

    public post_recycle_adapter(List<Post> post_List) {
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

        Log.d("Image", Post_List.get(position).getImage());
        if (Post_List.get(position).getImage().contains("mp4")) {
            holder.setmView(Post_List.get(position).getImage());
            holder.imageView.setVisibility(View.GONE);
            holder.mView.setVisibility(View.VISIBLE);

        } else {
            holder.setImageView(Post_List.get(position).getImage());
            holder.imageView.setVisibility(View.VISIBLE);


        }
        holder.setDesc(Post_List.get(position).getDesc());


    }

    @Override
    public int getItemCount() {
        return Post_List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private VideoView mView;
        private ImageView imageView;
        private TextView Desc;
        private View View;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            View = itemView;
            mView = View.findViewById(R.id.Video_post_view);
            imageView = View.findViewById(R.id.post_image_View);
        }

        public void setmView(String Video) {

            mView.setVideoURI(Uri.parse(Video));
            mView.start();
            imageView.setVisibility(android.view.View.GONE);

        }

        public void setImageView(String imageView) {

            Picasso.get().load(imageView).into(this.imageView);
            mView.setVisibility(android.view.View.GONE);
        }

        public void setDesc(String desc) {
            this.Desc = View.findViewById(R.id.txt_post_desc);
            this.Desc.setText(desc);
        }
    }
}