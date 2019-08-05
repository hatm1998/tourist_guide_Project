package com.example.touristguide.ShareItem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.touristguide.R;

public class Next_Info_Item extends AppCompatActivity {

    ImageView post_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next__info__item);
        post_image = findViewById(R.id.img_next_post);

        Intent intent = getIntent();
        Uri img = Uri.parse(intent.getStringExtra("URI"));
        if (intent.getIntExtra("Scale", 0) == 1) {
            post_image.setScaleType(ImageView.ScaleType.FIT_CENTER);

        } else
            post_image.setScaleType(ImageView.ScaleType.FIT_XY);

        post_image.setImageURI(img);
    }
}
