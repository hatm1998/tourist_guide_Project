package com.example.touristguide.CityActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.touristguide.R;
import com.example.touristguide.ShareItem.Add_new_post;
import com.example.touristguide.Utilis.Places;
import com.example.touristguide.Utilis.Post;
import com.example.touristguide.Utilis.place_recycle_view;
import com.example.touristguide.Utilis.post_recycle_adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class City_Main extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;

    private ImageView header_Image;

    private ProgressBar progressBar;
    private place_recycle_view place_recycle_view;

    private post_recycle_adapter post_recycle_view;



    private RecyclerView ads_list_view, Post_list_view;
    private List<Places> Adslist;
    private List<Post> postList;
    private FloatingActionButton fab;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header_main_city);
        final Intent intent = getIntent();
        final String CityID = intent.getStringExtra("Ads_id");
        progressBar = findViewById(R.id.header_city_progress);
        Adslist = new ArrayList<>();
        postList = new ArrayList<>();
        fab = findViewById(R.id.fab_add_Post);
        firebaseFirestore = FirebaseFirestore.getInstance();

        ads_list_view = findViewById(R.id.RC_City_Place);
        Post_list_view = findViewById(R.id.RC_City_post);


        place_recycle_view = new place_recycle_view(Adslist);
        ads_list_view.setAdapter(place_recycle_view);
        ads_list_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));


        final Query firstQuery = firebaseFirestore.collection("Cities").document(CityID).collection("places");

        firstQuery.addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {

                    if (documentChange.getType() == DocumentChange.Type.ADDED) {


                        Places places = documentChange.getDocument().toObject(Places.class);

                        Adslist.add(places);
                        place_recycle_view.notifyDataSetChanged();


                    }
                }
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(City_Main.this , Add_new_post.class);
                startActivity(intent);

            }
        });


        // ---------------------- ** ------------------------ //

        post_recycle_view = new post_recycle_adapter(postList);
        Post_list_view.setAdapter(post_recycle_view);
        Post_list_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


         Query SecoundQuery = firebaseFirestore.collection("post").orderBy("Date", Query.Direction.DESCENDING);

        SecoundQuery.addSnapshotListener( new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (!documentSnapshots.isEmpty()) {
                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {

                        if (documentChange.getType() == DocumentChange.Type.ADDED) {


                            Post post = documentChange.getDocument().toObject(Post.class);

                            postList.add(post);

                            place_recycle_view.notifyDataSetChanged();


                        }
                    }
                }
            }
        });

      /*  for (int x = 0; x < 5; x++) {
            Post post = new Post();
            postList.add(post);
            place_recycle_view.notifyDataSetChanged();
        }
*/

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.col_city);

        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);

        final Toolbar toolbar = findViewById(R.id.toolbarheader);
        toolbar.setTitle(CityID);

        setSupportActionBar(toolbar);

        progressBar.setVisibility(View.VISIBLE);

        header_Image = findViewById(R.id.header_img_city);
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Cities").document(CityID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                Picasso.get().load(task.getResult().get("Image").toString()).into(header_Image, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });


            }
        });


    }
}
