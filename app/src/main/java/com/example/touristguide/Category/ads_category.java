package com.example.touristguide.Category;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ads_category extends AppCompatActivity {

    VideoPlayerRecyclerView rc_category;
    ArrayList<Post> Adslist;
    FirebaseFirestore firebaseFirestore;
    VideoPlayerRecyclerAdapter ads_recycle_adapter;
    String cate = null;
    GridLayoutManager gridLayoutManager;


    Toolbar toolcate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_category);
        rc_category = findViewById(R.id.rec_category);
        Adslist = new ArrayList<>();
        Intent intent = getIntent();
        cate = intent.getStringExtra("Cate");
        Log.d("Categort1 = ", cate);

        toolcate = findViewById(R.id.tool_cate);
        toolcate.setTitle(cate);
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


        final Query firstQuery = firebaseFirestore.collection("post")
                .whereArrayContains("Categories", cate); // to show newest Ads at first


        firstQuery.addSnapshotListener(ads_category.this, new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (!documentSnapshots.isEmpty()) {

                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {

                        if (documentChange.getType() == DocumentChange.Type.ADDED) {


                            Post adspost = documentChange.getDocument().toObject(Post.class);

                            adspost.setPOSTID(documentChange.getDocument().getId());
                            Adslist.add(adspost);

                            ads_recycle_adapter.notifyDataSetChanged();


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
