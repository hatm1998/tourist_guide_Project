package com.example.touristguide.video_player;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.transition.CircularPropagation;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.touristguide.Commnets.Bottom_Sheet_Comment;
import com.example.touristguide.R;
import com.example.touristguide.text_component;
import com.example.touristguide.video_player.models.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class VideoPlayerViewHolder extends RecyclerView.ViewHolder {

    Button btn_Location;
    FirebaseFirestore firebaseFirestore;
    FrameLayout media_container;
    TextView user_name , Desc;
    CircleImageView image_profile;
    ImageView post_image_View, volumeControl;
    ProgressBar progressBar;
    View parent;
    RequestManager requestManager;
    Button btn_Comment;

    public VideoPlayerViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        user_name = itemView.findViewById(R.id.Name_User_profile);
        Desc = itemView.findViewById(R.id.txt_post_desc);
        image_profile = itemView.findViewById(R.id.Pic_User_profile);
        media_container = itemView.findViewById(R.id.media_container_post);
        post_image_View = itemView.findViewById(R.id.post_image_View);
        progressBar = itemView.findViewById(R.id.progress_picuser_inpost_profile);
        volumeControl = itemView.findViewById(R.id.volume_control);
        btn_Location = itemView.findViewById(R.id.btn_LocationPost_profile);
        btn_Comment=itemView.findViewById(R.id.btn_CommentPost_profile);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void onBind(final Context context , final Post post, RequestManager requestManager) {
        this.requestManager = requestManager;
        parent.setTag(this);
        this.requestManager
                .load(post.getMedia_url())
                .into(post_image_View);

        btn_Comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bottom_Sheet_Comment Comment = new Bottom_Sheet_Comment(context);
                Comment.show(((FragmentActivity)context).getSupportFragmentManager(), "Comment");
            }
        });

        Desc.setText(post.getDesc());
        firebaseFirestore.collection("User").document(post.getUserID()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Picasso.get().load(task.getResult().get("Image_User").toString()).into(image_profile);
                user_name.setText(task.getResult().get("Username").toString());
                progressBar.setVisibility(View.GONE);
            }
        });


        btn_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", post.getLocation().getLatitude(),post.getLocation().getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                parent.getContext().startActivity(intent);
            }
        });


    }


}














