package com.example.touristguide.Event.Display_Event;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.touristguide.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class Display_Event_page extends AppCompatActivity {

    private ImageView Img_Event;
    private ProgressBar progress;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__event_page);
        context=this;
        Intent intent =getIntent();
        String Img=intent.getStringExtra("Img");
        Img_Event=findViewById(R.id.Img_dispaly_Event);
        progress=findViewById(R.id.progress_display_Event);
        //  set Img (Event) .
        Picasso.get().load(Img).into(Img_Event,new Callback() {
            @Override
            public void onSuccess() {
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }

        });
    }
}
