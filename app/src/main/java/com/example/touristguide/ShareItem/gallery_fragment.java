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
import com.example.touristguide.Utilis.GridImageAdapter;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.util.ArrayList;

public class gallery_fragment extends Fragment {

    private GridView GallaryGrid;
    private ImageView ImagePic;
    private  TextView txt_next;

    private String mappend = "file:/";

    private Spinner Sp_Dir;
    private ArrayList<String> directories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        imageconfig();
        GallaryGrid = view.findViewById(R.id.GV_gallery_pic);
        ImagePic = view.findViewById(R.id.img_share);
        txt_next = view.findViewById(R.id.txt_next_gellary);

        Sp_Dir = view.findViewById(R.id.sp_Dirction);

        directories = new ArrayList<>();
       // getActivity().imageLoader.init(ImageLoaderConfiguration.createDefault(getBaseCont‌​ext()));
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
			.build();
        ImageLoader.getInstance().init(config);

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

            setupGridView(directories.get(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setupGridView(String SelectedDir)
    {
        final ArrayList<String> imgURL = FileSearch.getFilepaths(SelectedDir);


        // set the grid column width
        int Gridwidht = getResources().getDisplayMetrics().widthPixels;
        int imagewidth = Gridwidht / 3; //  <----- this is number of columns GridView
        GallaryGrid.setColumnWidth(imagewidth);

        GridImageAdapter adapter = new GridImageAdapter(getActivity() , R.layout.gridimageview,mappend,imgURL);
        GallaryGrid.setAdapter(adapter);

    }

    private void imageconfig()
    {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext())
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                .build();
    }
}
