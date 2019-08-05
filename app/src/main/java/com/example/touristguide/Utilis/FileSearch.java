package com.example.touristguide.Utilis;

import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class FileSearch {

    public static ArrayList<String> getDirection(String Dir) {
        ArrayList<String> pathArray = new ArrayList<>();

        File file = new File(Dir);
        File[] listfile = file.listFiles();

        for (int i = 0; i < listfile.length; i++) {
            if (listfile[i].isDirectory()) {

                pathArray.add(listfile[i].getAbsolutePath());
            }
        }
        return pathArray;

    }

    public static ArrayList<String> getpicpaths(String Dir) {
        ArrayList<String> pathArray = new ArrayList<>();

        File file = new File(Dir);
        File[] listfile = file.listFiles();

        for (int i = 0; i < listfile.length; i++) {
            if (listfile[i].isFile()) {

                if (!listfile[i].getAbsolutePath().contains(".mp4")) {
                    pathArray.add(listfile[i].getAbsolutePath());
                    Log.d("ImagePathAb", listfile[i].getAbsolutePath());
                }
                }
        }
        return pathArray;

    }

    public static ArrayList<String> getVideopaths(String Dir) {
        ArrayList<String> pathArray = new ArrayList<>();

        File file = new File(Dir);
        File[] listfile = file.listFiles();

        for (int i = 0; i < listfile.length; i++) {
            if (listfile[i].isFile()) {

                if (listfile[i].getAbsolutePath().contains(".mp4")) {
                    pathArray.add(listfile[i].getAbsolutePath());
                    Log.d("ImagePathAb", listfile[i].getAbsolutePath());
                }
            }
        }
        return pathArray;

    }
}
