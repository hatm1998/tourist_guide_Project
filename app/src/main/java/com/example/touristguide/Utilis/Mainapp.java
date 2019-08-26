package com.example.touristguide.Utilis;

import android.app.Application;
import android.content.Context;

public class Mainapp extends Application {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LoacalHelper.onAttach(newBase,"en"));
    }
}
