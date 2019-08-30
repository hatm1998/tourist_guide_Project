package com.example.touristguide.Bookmark;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BookMarks extends AppCompatActivity {

    VideoPlayerRecyclerView rc_category;
    ArrayList<Post> Adslist;
    FirebaseFirestore firebaseFirestore;
    VideoPlayerRecyclerAdapter ads_recycle_adapter;
    // String cate = null;
    GridLayoutManager gridLayoutManager;
    FirebaseAuth mAuth;

    Toolbar toolcate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_category);
        rc_category = findViewById(R.id.rec_category);
        Adslist = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        gridLayoutManager = new GridLayoutManager(getBaseContext(), 1);

        GridLayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 1);
        rc_category.setLayoutManager(layoutManager);


        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        rc_category.addItemDecoration(itemDecorator);
        Adslist = new ArrayList<>();
        rc_category.setPosts(Adslist);
        ads_recycle_adapter = new VideoPlayerRecyclerAdapter(getBaseContext(), Adslist, initGlide());


        rc_category.setAdapter(ads_recycle_adapter);

        firebaseFirestore = FirebaseFirestore.getInstance();


        final Query firstQuery = firebaseFirestore.collection("post"); // to show newest Ads at first


        firstQuery.addSnapshotListener(BookMarks.this, new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (!documentSnapshots.isEmpty()) {

                    for (final DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {

                        if (documentChange.getType() == DocumentChange.Type.ADDED) {

                            Log.d("Postnumber", String.valueOf(1));

                            firebaseFirestore.collection("User").document(mAuth.getCurrentUser().getUid())
                                    .collection("Bookmark").document(documentChange.getDocument().getId())
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()) {
                                        Post adspost = documentChange.getDocument().toObject(Post.class);
                                        adspost.setPOSTID(documentChange.getDocument().getId());
                                        Adslist.add(adspost);
                                        ads_recycle_adapter.notifyDataSetChanged();
                                    }
                                }
                            });


                        }
                    }
                }
            }
        });


    }

    private RequestManager initGlide() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background);

        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }

    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        rc_category.releasePlayer();
        finish();


    }
}
