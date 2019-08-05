package com.example.touristguide.ShareItem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
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
import android.widget.VideoView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.touristguide.R;
import com.example.touristguide.Utilis.FilePath;
import com.example.touristguide.Utilis.FileSearch;
import com.example.touristguide.Utilis.GridImageAdapter;

import com.example.touristguide.Utilis.GridVideoAdpter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;

import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


import java.util.ArrayList;

public class camera_fragment extends Fragment {

    private GridView GallaryGrid;
    private VideoView ImagePic;
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
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
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

        setImage("https://firebasestorage.googleapis.com/v0/b/touristguide-81502.appspot.com/o/Petra%20Documentary_%20Lost%20City%20Of%20Stone%20-%20Documentary%20HD%200s%20-%2060s%20(6xQaEZbVras).mp4?alt=media&token=6bb6f1e4-22c5-4415-93b2-03e17e690dc5", ImagePic, mappend);

        //init();
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
        final ArrayList<String> imgURL = FileSearch.getVideopaths(SelectedDir) ;


        // set the grid column width
        int Gridwidht = getResources().getDisplayMetrics().widthPixels;
        int imagewidth = Gridwidht / 3; //  <----- this is number of columns GridView
        GallaryGrid.setColumnWidth(imagewidth);

        GridVideoAdpter adapter = new GridVideoAdpter(getActivity(), R.layout.gridvideoview, mappend, imgURL);


        GallaryGrid.setAdapter(adapter);
        setImage("https://firebasestorage.googleapis.com/v0/b/touristguide-81502.appspot.com/o/Petra%20Documentary_%20Lost%20City%20Of%20Stone%20-%20Documentary%20HD%200s%20-%2060s%20(6xQaEZbVras).mp4?alt=media&token=6bb6f1e4-22c5-4415-93b2-03e17e690dc5", ImagePic, mappend);


        GallaryGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setImage(imgURL.get(i), ImagePic, mappend);


            }
        });

    }

    private void setImage(final String imageURI, VideoView image, String mappend) {
      image.setVideoURI(Uri.parse(imageURI));

      image.setOnInfoListener(new MediaPlayer.OnInfoListener() {
          @Override
          public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {


              if (i == mediaPlayer.MEDIA_INFO_BUFFERING_START)
              {
                  mpProgressBar.setVisibility(View.VISIBLE);
              }
              else if (i == mediaPlayer.MEDIA_INFO_BUFFERING_END)
              {
                  mpProgressBar.setVisibility(View.INVISIBLE);
              }
              return false;
          }
      });
      image.requestFocus();
      image.start();
    }


}
