package com.example.touristguide.Profile.Fragment_Picture;


        import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.R;
        import com.example.touristguide.video_player.models.Post;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.firestore.DocumentChange;
        import com.google.firebase.firestore.EventListener;
        import com.google.firebase.firestore.FirebaseFirestore;
        import com.google.firebase.firestore.FirebaseFirestoreException;
        import com.google.firebase.firestore.Query;
        import com.google.firebase.firestore.QuerySnapshot;

        import java.util.ArrayList;

public class Fragment_Picture extends Fragment {

    private RecyclerView RCV_post;
    private ArrayList<setPicture> list;
    private adapter_picture_inprofile adapter;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;

    public Fragment_Picture() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_picture_inprofile, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        RCV_post=view.findViewById(R.id.RCV_picture_inprofile);
        list=new ArrayList<>();
        adapter=new adapter_picture_inprofile(getContext(),list);

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
                            list.add(new setPicture(documentChange.getDocument().getId(),post.getMedia_url()));
                            adapter.notifyDataSetChanged();


                        }
                    }
                }
            }
        });
        adapter=new adapter_picture_inprofile(getContext(),list);
        RCV_post.setLayoutManager(new GridLayoutManager(getContext(),3));
        RCV_post.setAdapter(adapter);

        return view;
    }

}