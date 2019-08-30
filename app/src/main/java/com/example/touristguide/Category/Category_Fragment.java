package com.example.touristguide.Category;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.touristguide.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Category_Fragment extends Fragment {


    private DrawerLayout drawerLayout;
    GridView gridView;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    public Category_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_Categories);

        gridView = view.findViewById(R.id.categort_grid);


        String chip_Name[] = getResources().getStringArray(R.array.chip_Name);

        List<Integer> cateimg = new ArrayList<>();


        cateimg.add(R.drawable.medicalheart);
        cateimg.add(R.drawable.petra1);
        cateimg.add(R.drawable.tetris);
        cateimg.add(R.drawable.retail);
        cateimg.add(R.drawable.pyramids);
        cateimg.add(R.drawable.tropical);
        cateimg.add(R.drawable.sprout);
        cateimg.add(R.drawable.canvas);


        category_adapter adapter = new category_adapter(getContext(), cateimg, chip_Name);

        gridView.setAdapter(adapter);


        return view;
    }

}
