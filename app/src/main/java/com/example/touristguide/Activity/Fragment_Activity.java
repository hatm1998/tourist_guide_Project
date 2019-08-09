package com.example.touristguide.Activity;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.R;
import com.example.touristguide.Utilis.Adapter_Activity;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Fragment_Activity extends Fragment {

    private Fragment fragment;
    private GridView Rev_activity;
    private ArrayList<setGovernorates> list;
    private Adapter_Activity adapter_activity ;
    private FirebaseFirestore firebaseFirestore;

    public Fragment_Activity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        Rev_activity= (view.findViewById(R.id.Rev_Activity));
        firebaseFirestore = FirebaseFirestore.getInstance();

        list=new ArrayList<>();



        final List <String> img = new ArrayList();
        final List<String> cityname  =  new ArrayList<>();
        final List <String> ID = new ArrayList();
      Query query =  firebaseFirestore.collection("Cities");

      query.addSnapshotListener( new EventListener<QuerySnapshot>() {
          @Override
          public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {


              if (!queryDocumentSnapshots.isEmpty()) {

                  for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {

                      if (documentChange.getType() == DocumentChange.Type.ADDED) {
                          cityname.add(documentChange.getDocument().getId());
                          img.add( documentChange.getDocument().getString("Image"));
                          ID.add(documentChange.getDocument().getId());                      }
                  }
              }
          }
      });


        adapter_activity=new Adapter_Activity(getContext(),img , cityname , ID);

        Rev_activity.setAdapter(adapter_activity);

        return view;
    }

}