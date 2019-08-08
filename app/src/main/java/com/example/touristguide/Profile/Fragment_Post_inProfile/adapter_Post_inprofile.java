package com.example.touristguide.Profile.Fragment_Post_inProfile;

        import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.ProgressBar;
        import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.R;
        import com.squareup.picasso.Callback;
        import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class adapter_Post_inprofile  extends RecyclerView.Adapter<adapter_Post_inprofile.ViewHolder> {


    public  static class ViewHolder extends RecyclerView.ViewHolder{
          private CircleImageView pic_user;
          private ImageView IMG_Post;
          private TextView Name_User,Document,date;
          private Button btn_Location,btn_like,btn_comment,btn_share;
          private ProgressBar progressBar_pic_user_inPost,ProgressBar_ImagePost;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pic_user=itemView.findViewById(R.id.Pic_User_profile);
            Name_User=itemView.findViewById(R.id.Name_User_profile);
            IMG_Post=itemView.findViewById(R.id.ImgPost_profile);
            Document=itemView.findViewById(R.id.DocumentPost_profile);
            date=itemView.findViewById(R.id.DatePost_profile);
            btn_Location=itemView.findViewById(R.id.btn_LocationPost_profile);
            btn_like=itemView.findViewById(R.id.btn_likePost_profile);
            btn_comment=itemView.findViewById(R.id.btn_CommentPost_profile);
            btn_share=itemView.findViewById(R.id.btn_SharePost_profile);
            progressBar_pic_user_inPost=itemView.findViewById(R.id.progress_picuser_inpost_profile);
            ProgressBar_ImagePost=itemView.findViewById(R.id.progress_ImagePost_profile);
        }
    }

    private Context context;
    private ArrayList<setPost> list;
    public adapter_Post_inprofile(Context context , ArrayList<setPost> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.style_post_in_profile,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final setPost pos=list.get(position);

        //  set Pic (User)
        Picasso.get().load(pos.getPic_user()).into(holder.pic_user,new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar_pic_user_inPost.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }

        });

        // set Name (User)
        holder.Name_User.setText(pos.getYourname());

        // set Image (Post)
        Picasso.get().load(pos.getImg_post()).into(holder.IMG_Post,new Callback() {
            @Override
            public void onSuccess() {
                holder.ProgressBar_ImagePost.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }

        });

        // set Document for Post .
        holder.Document.setText(pos.getDocument());

        // set Date
        //holder.date.setText();

        // ONClick btn Location
        holder.btn_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // ONClick btn Like
        holder.btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // ONClick btn Comment
        holder.btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // ONClick btn Share
        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
