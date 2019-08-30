package com.example.touristguide.notification_saved;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.touristguide.R;
import com.example.touristguide.video_player.VideoPlayerRecyclerAdapter;
import com.example.touristguide.video_player.VideoPlayerRecyclerView;
import com.example.touristguide.video_player.models.Post;
import com.example.touristguide.video_player.util.VerticalSpacingItemDecorator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_notification  extends RecyclerView.Adapter<Adapter_notification.ViewHolder> {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private static Context context;
    RequestManager requestManager;
    int commentcount = 0, likecont = 0;
    String mUsername;

    public  static class ViewHolder extends RecyclerView.ViewHolder{
           private CircleImageView Img_event;
           private TextView txt_subject,txt_Document,txt_Date , txt_location;
           private ProgressBar progressBar;
           private CardView card_display_Post;

        public void setTxt_location(String txt_location) {
            this.txt_location.setText(txt_location);
        }

        public void setImg_event(String img_event) {
            Picasso.get().load(img_event).into(Img_event, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });

        }

        public void setTxt_subject(String txt_subject) {
            this.txt_subject.setText(txt_subject);

        }

        public void setTxt_Document(String txt_Document) {
            this.txt_Document.setText(txt_Document);
            if (txt_Document.contains("Liked"))
            {
                Drawable img = context.getResources().getDrawable( R.drawable.check_state_love);
                this.txt_Document.setCompoundDrawablesRelative(img,null,null,null);
            }
            else
            {
                Drawable img = context.getResources().getDrawable( R.drawable.style_btn_comment);
                this.txt_Document.setCompoundDrawablesRelative(img,null,null,null);
            }
        }

        public void setTxt_Date(String txt_Date) {
            this.txt_Date.setText(txt_Date);
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Img_event =itemView.findViewById(R.id.img_event_notification_page);
            txt_subject =itemView.findViewById(R.id.txt_subject_event);
            txt_Document =itemView.findViewById(R.id.txt_document_noti_page);
            txt_Date =itemView.findViewById(R.id.txt_date_noti_page);
            progressBar = itemView.findViewById(R.id.pr_noti_image);
            txt_location = itemView.findViewById(R.id.txt_notification_location);
            card_display_Post=itemView.findViewById(R.id.card_notification);


        }


    }
    private ArrayList<setNotification> list;
    public Adapter_notification(Context context , ArrayList<setNotification> list , RequestManager requestManager){
        this.context=context;
        this.list=list;
        this.requestManager=requestManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.style_notification,parent,false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        firebaseFirestore.collection("User").document(list.get(position).getUserSender())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                holder.setTxt_subject(task.getResult().get("Username").toString());
                holder.setImg_event(task.getResult().get("Image_User").toString());
            }
        });
        holder.setTxt_Document(list.get(position).getMsg());

        firebaseFirestore.collection("post").document(list.get(position).getPostInfo())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    holder.setTxt_location(task.getResult().get("CityName").toString());
                }

            }
        });


       // Toast.makeText(context,list.get(position).getPostInfo() , Toast.LENGTH_LONG).show();

       // Log.d("Info1",list.get(position).getPostInfo());

        long MilliSecound = list.get(position).getDate().getTime();

        java.util.Date today = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        String dateString = null;
        String datetoday = DateFormat.format("dd", new Date(MilliSecound)).toString();

        Log.d("Datecomment", datetoday + " / " + calendar.get(Calendar.DAY_OF_MONTH));
        if (Integer.parseInt(datetoday) == calendar.get(Calendar.DAY_OF_MONTH)) {
            dateString = DateFormat.format("HH:mm aa", new Date(MilliSecound)).toString();
        } else {
            dateString = DateFormat.format("dd MMM yyyy", new Date(MilliSecound)).toString();
        }

        holder.setTxt_Date(dateString);

        holder.card_display_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.style_display_post_as_dialog);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);

               final  VideoPlayerRecyclerView mRecyclerView = dialog.findViewById(R.id.RCV_Post_As_Dialog);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                mRecyclerView.setLayoutManager(layoutManager);
                VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
                mRecyclerView.addItemDecoration(itemDecorator);
              final   ArrayList<Post> posts = new ArrayList<>();
                mRecyclerView.setPosts(posts);

               final VideoPlayerRecyclerAdapter adapter = new VideoPlayerRecyclerAdapter(context,posts, initGlide());
                mRecyclerView.setAdapter(adapter);

                firebaseFirestore.collection("post").document(list.get(position).getPostInfo())
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
    private RequestManager initGlide() {
        RequestOptions options = new RequestOptions();

        return Glide.with(context)
                .setDefaultRequestOptions(options);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
