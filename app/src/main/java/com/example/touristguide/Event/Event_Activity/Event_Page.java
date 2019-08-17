package com.example.touristguide.Event.Event_Activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.touristguide.R;
import com.example.touristguide.Event.Event_Activity.silder_Image.adapter_Image_Slider;
import com.example.touristguide.Event.Event_Activity.silder_Image.set_Image;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Event_Page extends AppCompatActivity {

    // For Slider Image .
    private static ViewPager Pager_Slider_Img;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<set_Image> imageModelArrayList;
   private  String Image_event[]={
          "https://firebasestorage.googleapis.com/v0/b/realtimefirebase-aaf23.appspot.com/o/Event%2FZyad-Saleh-Autostrad-%40-Amman-Street-Food-Park-300x196.jpg?alt=media&token=03cc86bb-40b0-48a4-9832-ebe77346a621"
           ,
           "https://firebasestorage.googleapis.com/v0/b/realtimefirebase-aaf23.appspot.com/o/Event%2F6498.jpg?alt=media&token=7e4884fd-ac3c-4082-a758-928bc830cd17"
           ,
            "https://firebasestorage.googleapis.com/v0/b/realtimefirebase-aaf23.appspot.com/o/Event%2Feid-al-adha-bazar-at-regency-palace-amman.jpg?alt=media&token=529ff224-29b7-4f85-82fa-619b473a7030"
           ,
            "https://firebasestorage.googleapis.com/v0/b/realtimefirebase-aaf23.appspot.com/o/Event%2Fshajara_eid_adha.jpg?alt=media&token=fb5e9bca-2170-4ad0-ab53-2ba510d403e4"
    };
    private  String Image_city[]={
            "https://firebasestorage.googleapis.com/v0/b/realtimefirebase-aaf23.appspot.com/o/Amman%2Famman_jabal_amman_view.jpg?alt=media&token=32b2640b-1766-4730-b854-6d6f17bc9380"
            ,
            "https://firebasestorage.googleapis.com/v0/b/realtimefirebase-aaf23.appspot.com/o/Amman%2Fimage_processing20180627-4-pxz4wu.jpg?alt=media&token=22e30e20-1a6d-4f90-a98f-a7d37b9d2e54"
            ,
              "https://firebasestorage.googleapis.com/v0/b/realtimefirebase-aaf23.appspot.com/o/Amman%2Famman-w_0.jpg?alt=media&token=049420b0-e6af-45c6-a9fd-ff44f0bf1c98"
            ,
            "https://firebasestorage.googleapis.com/v0/b/realtimefirebase-aaf23.appspot.com/o/Amman%2Fdownload.jpg?alt=media&token=1589aadc-5774-4a68-83a6-8c2610274a17"
    };
    private Timer swipeTimer;


    // For Event .
    private RecyclerView RCV_Events;
    private ArrayList<set_Events> List;
    private Adapter_Events adapter_events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event__page);

        RCV_Events =findViewById(R.id.REV_Event_page);

        imageModelArrayList=new ArrayList<>();

        // add Image (Slider) .
        for(int i=0;i<Image_city.length;i++)
        imageModelArrayList.add(new set_Image(Image_city[i]));
        init_Sliding_IMage();

        // add All Event .
        List=new ArrayList<>();
        for(int i=0;i<Image_event.length ;i++)
            List.add(new set_Events(Image_event[i],"",new Date()));

        adapter_events=new Adapter_Events(this,List);
        RCV_Events.setLayoutManager(new GridLayoutManager(this,1));
        RCV_Events.setAdapter(adapter_events);
    }
    private void init_Sliding_IMage() {
        // set Image To silder In Home Page.
        Pager_Slider_Img = (ViewPager) findViewById(R.id.Pager_Slider_Img_Event_Page);
        Pager_Slider_Img.setAdapter(new adapter_Image_Slider(this,imageModelArrayList));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator_Event_Page);

        indicator.setViewPager(Pager_Slider_Img);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(3 * density);

        NUM_PAGES =imageModelArrayList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                Pager_Slider_Img.setCurrentItem(currentPage++, true);
            }
        };
        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1500, 1500);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

}

