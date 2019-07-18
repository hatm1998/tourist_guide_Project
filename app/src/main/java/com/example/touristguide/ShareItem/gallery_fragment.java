package com.example.touristguide.ShareItem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.touristguide.R;
import com.example.touristguide.Utilis.FilePath;
import com.example.touristguide.Utilis.FileSearch;

import java.util.ArrayList;

public class gallery_fragment extends Fragment {

    private GridView GallaryGrid;
    private ImageView ImagePic;
    private  TextView txt_next;

    private Spinner Sp_Dir;
    private ArrayList<String> directories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        GallaryGrid = view.findViewById(R.id.GV_gallery_pic);
        ImagePic = view.findViewById(R.id.img_share);
        txt_next = view.findViewById(R.id.txt_next_gellary);

        Sp_Dir = view.findViewById(R.id.sp_Dirction);

        directories = new ArrayList<>();


        txt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        init();
        return view;
    }

    private void init()
    {
        FilePath filePath = new FilePath();

        if(FileSearch.getDirection(filePath.Picture) != null)
        {
            directories = FileSearch.getDirection(filePath.Picture);
        }
        directories.add(filePath.CAMERA);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, directories);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Sp_Dir.setAdapter(adapter);

        Sp_Dir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext() , "You Selected : " + directories.get(i),Toast.LENGTH_LONG ).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
