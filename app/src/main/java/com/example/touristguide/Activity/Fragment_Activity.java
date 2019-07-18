package com.example.touristguide.Activity;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristguide.R;
import com.example.touristguide.Utilis.Adapter_Activity;

import java.util.ArrayList;

public class Fragment_Activity extends Fragment {

    private Fragment fragment;
    private RecyclerView Rev_activity;
    private ArrayList<setGovernorates> list;
    private Adapter_Activity adapter_activity ;

    public Fragment_Activity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        Rev_activity= (view.findViewById(R.id.Rev_Activity));
        list=new ArrayList<>();

        int img[]={R.drawable.deadsea
        ,R.drawable.deadsea
        ,R.drawable.deadsea
       ,R.drawable.deadsea
                ,R.drawable.deadsea
                ,R.drawable.deadsea
                ,R.drawable.deadsea};
        String name[]={
                "ziad Al kasaji"
                ,"mohmmed Al kasaji "
                ,"Basil Al kasaji "
                ,"Suhail Al kasaji "
                ,"Hatem "
                ,"HAZEM "
                ,"Azooz "};
        for(int i=0;i<img.length;i++)
           list.add(new setGovernorates(img[i],name[i]));
        adapter_activity=new Adapter_Activity(getActivity(),list);
        Rev_activity.setLayoutManager(new GridLayoutManager(getActivity(),1));
        Rev_activity.setNestedScrollingEnabled(false);
        Rev_activity.setAdapter(adapter_activity);

        return view;
    }

}