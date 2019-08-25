package com.example.touristguide.Profile.Fragment_Post_inProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.touristguide.R;
import com.example.touristguide.video_player.VideoPlayerRecyclerAdapter;
import com.example.touristguide.video_player.VideoPlayerRecyclerView;
import com.example.touristguide.video_player.models.Post;
import com.example.touristguide.video_player.util.VerticalSpacingItemDecorator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Fragment_Post extends Fragment {

    private static final String TAG = "MainActivity";

    public VideoPlayerRecyclerView mRecyclerView;
    private ArrayList<Post> posts = new ArrayList<>();
    private VideoPlayerRecyclerAdapter adapter;
    private FirebaseAuth mAuth;

    private FirebaseFirestore firebaseFirestore;


    public Fragment_Post() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_inprofile, container, false);

        Toast.makeText(getContext(),"CreateView",Toast.LENGTH_LONG).show();
        mRecyclerView = view.findViewById(R.id.recycler_view_Post_in_profile);
        mAuth = FirebaseAuth.getInstance();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);


        mRecyclerView.setPosts(posts);
        adapter = new VideoPlayerRecyclerAdapter(getActivity(),posts, initGlide());
        mRecyclerView.setAdapter(adapter);


        firebaseFirestore = FirebaseFirestore.getInstance();
        Query SecoundQuery = firebaseFirestore.collection("post")
                .whereEqualTo("UserID", mAuth.getCurrentUser().getUid());
        //.orderBy("Date", Query.Direction.DESCENDING);

        SecoundQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (!documentSnapshots.isEmpty()) {
                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {

                        if (documentChange.getType() == DocumentChange.Type.ADDED) {

                            Post post = documentChange.getDocument().toObject(Post.class);
                            post.setPOSTID(documentChange.getDocument().getId());
                            posts.add(post);
                            adapter.notifyDataSetChanged();


                        }
                    }
                }
            }
        });

        //------------------------


        return view;
    }

    @Override
    public void onPause() {
        mRecyclerView.releasePlayer();
        Toast.makeText(getContext(),"Fragment pause",Toast.LENGTH_LONG).show();
        super.onPause();
    }

    private RequestManager initGlide() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background);

        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }


    @Override
    public void onDestroyView() {
        if (mRecyclerView != null)
            mRecyclerView.releasePlayer();
        super.onDestroyView();
    }


}