package com.example.touristguide.notification_saved;

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
import java.util.Date;

public class notification_page extends Fragment {

    private RecyclerView RCV_not;
    private ArrayList<setNotification> list;
    private Adapter_notification adapter;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;

    public notification_page() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();



        RCV_not = view.findViewById(R.id.RCV_notification_page);
        list = new ArrayList<>();


        Query query = firebaseFirestore.collection("Notificarion")
                .whereEqualTo("UserID", mAuth.getCurrentUser().getUid());

        adapter = new Adapter_notification(getContext(), list);

        RCV_not.setLayoutManager(new GridLayoutManager(getContext(), 1));
        RCV_not.setAdapter(adapter);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (!documentSnapshots.isEmpty()) {
                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {

                        if (documentChange.getType() == DocumentChange.Type.ADDED) {

                            setNotification Notification = documentChange.getDocument().toObject(setNotification.class);
                            list.add(Notification);
                            adapter.notifyDataSetChanged();


                        }
                    }
                }
            }
        });





        return view;
    }

}