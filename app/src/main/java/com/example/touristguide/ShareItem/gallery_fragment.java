package com.example.touristguide.ShareItem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.touristguide.R;
import com.example.touristguide.Utilis.FilePath;
import com.example.touristguide.Utilis.FileSearch;
import com.example.touristguide.Utilis.GridImageAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;

import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


import java.util.ArrayList;

public class gallery_fragment extends Fragment {

    private GridView GallaryGrid;
    private ImageView ImagePic;
    private ProgressBar mpProgressBar;
    private TextView txt_next;

    private String imagePushURI;
    private ImageButton btn_imagescale;
    private String mappend = "file:/";

    private Integer scaletype = 1;
    private Spinner Sp_Dir;
    private ArrayList<String> directories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        GallaryGrid = view.findViewById(R.id.GV_gallery_pic);
        ImagePic = view.findViewById(R.id.img_share);
        btn_imagescale = view.findViewById(R.id.btn_gellery_photo_scale);
        txt_next = view.findViewById(R.id.txt_next_gellary);
        mpProgressBar = view.findViewById(R.id.gallery_progress);
        Sp_Dir = view.findViewById(R.id.sp_Dirction);

        directories = new ArrayList<>();

        btn_imagescale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ImagePic.getScaleType() == ImageView.ScaleType.FIT_CENTER) {
                    ImagePic.setScaleType(ImageView.ScaleType.FIT_XY);
                    scaletype = 2;
                } else {
                    ImagePic.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    scaletype = 1;
                }
            }
        });
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                .build();
        ImageLoader.getInstance().init(config);

        txt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getActivity(), Next_Info_Item.class);

                intent.putExtra("URI", imagePushURI);
                intent.putExtra("Scale", scaletype);
                getActivity().startActivity(intent);
            }
        });

        init();
        return view;
    }

    private void init() {
        FilePath filePath = new FilePath();

        if (FileSearch.getDirection(filePath.Picture) != null) {
            directories = FileSearch.getDirection(filePath.Picture);
        }
        directories.add(filePath.CAMERA);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, directories);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Sp_Dir.setAdapter(adapter);

        Sp_Dir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                setupGridView(directories.get(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setupGridView(String SelectedDir) {
        final ArrayList<String> imgURL = FileSearch.getpicpaths(SelectedDir) ;


        // set the grid column width
        int Gridwidht = getResources().getDisplayMetrics().widthPixels;
        int imagewidth = Gridwidht / 3; //  <----- this is number of columns GridView
        GallaryGrid.setColumnWidth(imagewidth);

        GridImageAdapter adapter = new GridImageAdapter(getActivity(), R.layout.gridimageview, mappend, imgURL);


            GallaryGrid.setAdapter(adapter);
            setImage(imgURL.get(0), ImagePic, mappend);


        GallaryGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setImage(imgURL.get(i), ImagePic, mappend);


            }
        });

    }

    private void setImage(final String imageURI, ImageView image, String mappend) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(mappend + imageURI, image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                mpProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                mpProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mpProgressBar.setVisibility(View.INVISIBLE);

                imagePushURI = imageURI;
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                mpProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }


}
