package com.example.touristguide.Commnets;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.R;
import com.example.touristguide.video_player.VideoPlayerViewHolder;
import com.example.touristguide.video_player.models.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class Bottom_Sheet_Comment extends BottomSheetDialogFragment {
    private Context context;
    private String ID;
    private TextView txt_comment;
    private Button btn_send;
    private String mUserName;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private String UserPost;

    public Bottom_Sheet_Comment(Context context, String ID, String userID) {
        this.context = context;
        this.ID = ID;
        UserPost = userID;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.comments_page, container, false);
        Button btn_menu = view.findViewById(R.id.btn_menu_comment);
        txt_comment = view.findViewById(R.id.txt_Wirte_comment);
        btn_send = view.findViewById(R.id.btn_comment_send);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                mUserName = task.getResult().get("Username").toString();
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> comment = new HashMap<>();
                comment.put("comment", txt_comment.getText().toString());
                comment.put("UserID", mAuth.getCurrentUser().getUid().toString());
                comment.put("Date", new Date());

                firebaseFirestore.collection("post").document(ID)
                        .collection("Comment").add(comment).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (!mAuth.getCurrentUser().getUid().equals(UserPost))
                            firebaseFirestore.collection("Token").document(UserPost)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    Reqnot(task.getResult().get("Token").toString(), mUserName + " " + context.getResources().getString(R.string.NotiComment));

                                    Toast.makeText(context, "ID : " + task.getResult().get("Token").toString(), Toast.LENGTH_LONG).show();


                                    HashMap<String,Object> Notificationmap = new HashMap<>();
                                    Notificationmap.put("Msg" , mUserName + " " + context.getResources().getString(R.string.NotiComment));
                                    Notificationmap.put("date" , new Date());
                                    Notificationmap.put("UserID",UserPost);
                                    Notificationmap.put("UserSender",mAuth.getCurrentUser().getUid());
                                    Notificationmap.put("PostInfo" , ID);

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
                                    txt_comment.setText("");
                                }
                            });
                        else
                            txt_comment.setText("");
                    }

                });
            }
        });

        final RecyclerView RCV_commment = view.findViewById(R.id.RCV_comment);
        final ArrayList<set_Comment> list = new ArrayList<>();

        final Adapter_Comments adapter_comments = new Adapter_Comments(this.context, list);
        RCV_commment.setLayoutManager(new GridLayoutManager(this.context, 1));
        RCV_commment.setAdapter(adapter_comments);


        Query SecoundQuery = firebaseFirestore.collection("post").document(ID).collection("Comment")
                .orderBy("Date", Query.Direction.DESCENDING);

        SecoundQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (!documentSnapshots.isEmpty()) {
                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {

                        if (documentChange.getType() == DocumentChange.Type.ADDED) {

                            set_Comment post = documentChange.getDocument().toObject(set_Comment.class);

                            list.add(post);
                            adapter_comments.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
//       for(int i=0;i<20;i++)
//           list.add(new set_Comment("","",new Date()));
//
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

    public void Reqnot(String ID, String Msg) {
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


