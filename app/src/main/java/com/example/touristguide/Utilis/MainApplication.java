package com.example.touristguide.Utilis;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LoacalHelper.onAttach(base,"en"));

    }

    @Override
    public void onCreate() {
        super.onCreate();


    }
}
