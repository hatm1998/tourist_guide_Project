package com.example.touristguide.Profile.Fragment_Post_inProfile;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.R;
import com.example.touristguide.Utilis.Post;
import com.example.touristguide.Utilis.adapter_Post_inprofile;
import com.example.touristguide.Utilis.post_recycle_adapter;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fragment_Post extends Fragment {


    private RecyclerView  Post_list_view;
    private List<Post> postList;
    private post_recycle_adapter adapter;
    private FirebaseFirestore firebaseFirestore;
    private post_recycle_adapter post_recycle_view;


    public Fragment_Post() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_inprofile, container, false);

        postList = new ArrayList<>();

        firebaseFirestore = FirebaseFirestore.getInstance();


        Post_list_view = view.findViewById(R.id.RCV_post_inprofile);



        post_recycle_view = new post_recycle_adapter(postList);
        Post_list_view.setAdapter(post_recycle_view);
        Post_list_view.setLayoutManager(new GridLayoutManager(getContext(),1));


        Query SecoundQuery = firebaseFirestore.collection("post").orderBy("Date", Query.Direction.DESCENDING);

        SecoundQuery.addSnapshotListener( new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (!documentSnapshots.isEmpty()) {
                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {

                        if (documentChange.getType() == DocumentChange.Type.ADDED) {

                            Post post = documentChange.getDocument().toObject(Post.class);
                            postList.add(post);
                            post_recycle_view.notifyDataSetChanged();
                        }
                    }
                }
            }
        });

        //------------------------


        return view;
    }

}