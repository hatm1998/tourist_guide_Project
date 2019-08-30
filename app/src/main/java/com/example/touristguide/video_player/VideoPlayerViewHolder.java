package com.example.touristguide.video_player;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.touristguide.Commnets.Bottom_Sheet_Comment;
import com.example.touristguide.R;
import com.example.touristguide.video_player.models.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


public class VideoPlayerViewHolder extends RecyclerView.ViewHolder {

    Button btn_Location;
    FirebaseFirestore firebaseFirestore;
    FrameLayout media_container;
    TextView user_name, Desc;
    CircleImageView image_profile;
    ImageView post_image_View, volumeControl;
    ProgressBar progressBar, mainImageProgress;
    FirebaseAuth mAuth;
    View parent;
    String mUsername;
    RequestManager requestManager;
    Button btn_Comment;
    ToggleButton btn_Bookmark, btn_Fav;
    TextView numcomment, numlike;
    int commentcount = 0, likecont = 0;

    public VideoPlayerViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        user_name = itemView.findViewById(R.id.Name_User_profile);
        Desc = itemView.findViewById(R.id.txt_post_desc);
        image_profile = itemView.findViewById(R.id.Pic_User_profile);
        media_container = itemView.findViewById(R.id.media_container_post);
        post_image_View = itemView.findViewById(R.id.post_image_View);
        mainImageProgress = itemView.findViewById(R.id.progressPost);
        progressBar = itemView.findViewById(R.id.progress_picuser_inpost_profile);
        volumeControl = itemView.findViewById(R.id.volume_control);
        btn_Location = itemView.findViewById(R.id.btn_LocationPost_profile);
        btn_Comment = itemView.findViewById(R.id.btn_CommentPost_profile);
        btn_Bookmark = itemView.findViewById(R.id.btn_SharePost_profile);
        btn_Fav = itemView.findViewById(R.id.btn_likePost_profile);
        numcomment = itemView.findViewById(R.id.txt_Num_com);
        numlike = itemView.findViewById(R.id.txt_num_like);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    public void onBind(final Context context, final Post post, RequestManager requestManager) {
        this.requestManager = requestManager;
        parent.setTag(this);

        if (post.getMedia_url().contains(".mp4"))
            this.requestManager
                    .load(post.getMedia_url())
                    .into(post_image_View);
        else
            Picasso.get().load(post.getMedia_url())
                    .resize(200, 200).into(post_image_View, new Callback() {
                @Override
                public void onSuccess() {
                    mainImageProgress.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });


        firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                mUsername = (String) task.getResult().get("Username");
            }
        });

        btn_Comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bottom_Sheet_Comment Comment = new Bottom_Sheet_Comment(context, post.getPOSTID(), post.getUserID());

                Comment.show(((FragmentActivity) context).getSupportFragmentManager(), "Comment");
            }
        });


        Query SecoundQuery = firebaseFirestore.collection("post")
                .document(post.getPOSTID()).collection("Comment");


        SecoundQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (!documentSnapshots.isEmpty()) {
                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                        commentcount++;
                        numcomment.setText("Comment : " + commentcount);
                    }
                }
            }
        });

        final Query ThiredQuery = firebaseFirestore.collection("post")
                .document(post.getPOSTID()).collection("Fav");


        ThiredQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (!documentSnapshots.isEmpty()) {
                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                        if (documentChange.getType() == DocumentChange.Type.ADDED) {
                            likecont++;
                            numlike.setText("Like : " + likecont);
                        } else {
                            likecont--;
                            Toast.makeText(context, "Count = " + likecont, Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }
        });

        firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getUid())
                .collection("Bookmark").document(post.getPOSTID()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean isEmpty = task.getResult().exists();
                            if (isEmpty)
                                btn_Bookmark.setChecked(true);
                        }
                    }
                });

        firebaseFirestore.collection("post").document(post.getPOSTID())
                .collection("Fav").document(post.getPOSTID()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean isEmpty = task.getResult().exists();
                            if (isEmpty)
                                btn_Fav.setChecked(true);
                        }
                    }
                });


        btn_Fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (!b) {
                    firebaseFirestore.collection("post").document(post.getPOSTID())
                            .collection("Fav").document(mAuth.getCurrentUser().getUid()).delete();
                    likecont--;
                    numlike.setText("Like : " + likecont);

                } else {
                    HashMap<String, Object> exist = new HashMap<>();
                    firebaseFirestore.collection("post").document(post.getPOSTID())
                            .collection("Fav").document(mAuth.getCurrentUser().getUid())
                            .set(exist).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!mAuth.getCurrentUser().getUid().equals(post.getUserID()))
                                firebaseFirestore.collection("Token").document(post.getUserID())
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        Reqnot(task.getResult().get("Token").toString(), mUsername + " " + context.getResources().getString(R.string.NotiFav));

                                        HashMap<String,Object> Notificationmap = new HashMap<>();
                                        Notificationmap.put("Msg" , mUsername + " " + context.getResources().getString(R.string.NotiFav));
                                        Notificationmap.put("date" , new Date());
                                        Notificationmap.put("UserID",post.getUserID());
                                        Notificationmap.put("UserSender",mAuth.getCurrentUser().getUid());
                                        Notificationmap.put("PostInfo" , post.getPOSTID());

                                        firebaseFirestore.collection("Notificarion").document(UUID.randomUUID().toString())
                                                .set(Notificationmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful())
                                                {
                                                    Toast.makeText(context,"Notification Sent", Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        });

                                    }
                                });


                        }

                    });

                }


            }
        });


        btn_Bookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getUid())
                            .collection("Bookmark").document(post.getPOSTID()).delete();
                } else {
                    HashMap<String, Object> exist = new HashMap<>();
                    firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getUid())
                            .collection("Bookmark").document(post.getPOSTID()).set(exist);

                }

            }
        });

        if(!post.getDesc().equals(""))
        Desc.setText(post.getDesc());
        else
            Desc.setVisibility(View.GONE);
        firebaseFirestore.collection("User").document(post.getUserID()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Picasso.get().load(task.getResult().get("Image_User").toString()).into(image_profile);
                user_name.setText(task.getResult().get("Username").toString());
                progressBar.setVisibility(View.GONE);
            }
        });


       // Toast.makeText(context, (int) (post.getLoc().getLatitude() + post.getLoc().getLongitude()), Toast.LENGTH_LONG).show();
        btn_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", post.getLoc().getLatitude(), post.getLoc().getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                parent.getContext().startActivity(intent);
            }
        });


    }

    private void Reqnot(String ID, String Msg) {
        Notify notify = new Notify(ID, Msg);
        notify.execute();

    }

    public class Notify extends AsyncTask<Void, Void, Void> {

        private String ID, Msg;

        Notify(String Reciver, String Msg) {
            ID = Reciver;
            this.Msg = Msg;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                URL url = new URL("https://fcm.googleapis.com/fcm/send");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "key=AIzaSyARUYhU0qleq6Dlbj6ZQ9b0JXyB3bqsYIo");
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject json = new JSONObject();

                json.put("to", ID);

                JSONObject info = new JSONObject();
                info.put("body", Msg);   // Notification title
                info.put("title", "SafariAPP"); // Notification body
                info.put("content_available", "true");
                info.put("priority", "high");

                json.put("notification", info);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(json.toString());
                wr.flush();
                conn.getInputStream();

                // Toast.makeText(getBaseContext(),"Done",Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Log.d("ErrorOne", "" + e);
            }
            return null;
        }
    }


}














