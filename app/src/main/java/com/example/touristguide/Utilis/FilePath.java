package com.example.touristguide.Utilis;

import android.os.Environment;

public class FilePath {

    public String Root_Dir = Environment.getExternalStorageDirectory().getPath();

    public String Picture = Root_Dir + "/Picture";
    public String CAMERA = Root_Dir + "/DCIM/camera";


}
