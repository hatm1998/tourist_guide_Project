package com.example.touristguide.Profile.Fragment_Picture;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.touristguide.R;
import com.example.touristguide.Utilis.VideoRequestHandler;
import com.example.touristguide.video_player.VideoPlayerRecyclerAdapter;
import com.example.touristguide.video_player.VideoPlayerRecyclerView;
import com.example.touristguide.video_player.models.Post;
import com.example.touristguide.video_player.util.VerticalSpacingItemDecorator;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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
    private FirebaseFirestore firebaseFirestore;

    public adapter_picture_inprofile(Context context, ArrayList<setPicture> list) {
        this.context = context;
        this.list = list;
        firebaseFirestore=FirebaseFirestore.getInstance();
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
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

                    display_Post(holder,position);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        } else {

            initGlide().load(pos.getPicture()).into(holder.IMG_post );
            holder.progressBar.setVisibility(View.GONE);
            display_Post(holder,position);

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


    private void display_Post(@NonNull final ViewHolder holder, final int position){
        holder.IMG_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.style_display_post_as_dialog);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);

                final VideoPlayerRecyclerView mRecyclerView = dialog.findViewById(R.id.RCV_Post_As_Dialog);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                mRecyclerView.setLayoutManager(layoutManager);
                VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
                mRecyclerView.addItemDecoration(itemDecorator);
                final   ArrayList<Post> posts = new ArrayList<>();
                mRecyclerView.setPosts(posts);

                final VideoPlayerRecyclerAdapter adapter = new VideoPlayerRecyclerAdapter(context,posts, initGlide());
                mRecyclerView.setAdapter(adapter);

                firebaseFirestore.collection("post").document(list.get(position).getKey())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Post post = documentSnapshot.toObject(Post.class);
                                post.setPOSTID(documentSnapshot.getId());
                                Log.i("zozo",post.getMedia_url());
                                posts.add(post);
                                adapter.notifyDataSetChanged();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if (mRecyclerView != null)
                            mRecyclerView.releasePlayer();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
