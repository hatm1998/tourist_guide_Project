package com.example.touristguide.notification_saved;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.R;

import java.util.ArrayList;
import java.util.Date;

public class notification_page extends Fragment {

    private  RecyclerView RCV_not;
   private  ArrayList<setNotification>  list;
      private   Adapter_notification adapter ;
    public notification_page() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

         RCV_not=view.findViewById(R.id.RCV_notification_page);
        list= new ArrayList<>();

        for(int i=0;i<20;i++)
        list.add(new setNotification("","New Event in Amman ","Ziad Al-kasaji - Hatem Darwish .",new Date(1997,1,31)));
        adapter=new Adapter_notification(getContext(),list);

        RCV_not.setLayoutManager(new GridLayoutManager(getContext(),1));
        RCV_not.setAdapter(adapter);




        return view;
    }

}