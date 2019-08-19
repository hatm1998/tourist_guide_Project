package com.example.touristguide.ShareItem;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.touristguide.R;
import com.example.touristguide.Utilis.FilePath;
import com.example.touristguide.Utilis.FileSearch;
import com.example.touristguide.Utilis.GridVideoAdpter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class camera_fragment extends Fragment {

    private GridView GallaryGrid;
    public static VideoView ImagePic;

    private TextView txt_next;

    private static String imagePushURI;
    private ImageButton btn_video_vol;
    private String mappend = "file:/";

    private Integer scaletype = 1;
    private boolean voice = true;
    private Spinner Sp_Dir;
    private MediaController mediaController;
    private ArrayList<String> directories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        GallaryGrid = view.findViewById(R.id.GV_gallery_pic);
        ImagePic = view.findViewById(R.id.img_share);
        btn_video_vol = view.findViewById(R.id.btn_video_volume);
        txt_next = view.findViewById(R.id.txt_next_gellary);
        ImagePic.setMediaController(mediaController);
        ImagePic.pause();
        Sp_Dir = view.findViewById(R.id.sp_Dirction);

        directories = new ArrayList<>();


        btn_video_vol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (voice) {
                    ImagePic.setOnPreparedListener(PreparedListener);
                    btn_video_vol.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_volume_off_black_24dp));

                    voice = false;

                } else {
                    btn_video_vol.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_volume_up));
                    voice = true;
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


        //  setImage("https://firebasestorage.googleapis.com/v0/b/touristguide-81502.appspot.com/o/Petra%20Documentary_%20Lost%20City%20Of%20Stone%20-%20Documentary%20HD%200s%20-%2060s%20(6xQaEZbVras).mp4?alt=media&token=6bb6f1e4-22c5-4415-93b2-03e17e690dc5", ImagePic, mappend);

        init();
       // Log.d("VideoURI", imagePushURI);
        return view;
    }

    private void init() {
        FilePath filePath = new FilePath();

        if (FileSearch.getDirection(filePath.Picture) != null) {
            directories = FileSearch.getDirection(filePath.Picture);

        }
        directories.add(filePath.CAMERA);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, directories);
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
        final ArrayList<String> imgURL = FileSearch.getVideopaths(SelectedDir);


        // set the grid column width
        int Gridwidht = getResources().getDisplayMetrics().widthPixels;
        int imagewidth = Gridwidht / 3; //  <----- this is number of columns GridView
        GallaryGrid.setColumnWidth(imagewidth);

        GridVideoAdpter adapter = new GridVideoAdpter(getActivity(), R.layout.gridvideoview, mappend, imgURL);

        GallaryGrid.setAdapter(adapter);
        if (imgURL.size() > 0) {
            setImage(imgURL.get(0), ImagePic, mappend);
        }

        else
            Toast.makeText(getActivity(), R.string.ErrorFilePic, Toast.LENGTH_SHORT).show();


        GallaryGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setImage(imgURL.get(i), ImagePic, mappend);


            }
        });
        ImagePic.pause();

    }

    private void setImage(final String imageURI, VideoView image, String mappend) {
        image.setVideoURI(Uri.parse(imageURI));
        imagePushURI = imageURI;
        image.requestFocus();
        image.start();
    }

    MediaPlayer.OnPreparedListener PreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {


            mediaPlayer.setLooping(true);
        }
    };

}
