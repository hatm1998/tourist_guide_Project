package com.example.touristguide.Utilis;

import java.io.File;
import java.util.ArrayList;

public class FileSearch {

    public static ArrayList<String> getDirection(String Dir)
    {
        ArrayList<String> pathArray = new ArrayList<>();

        File file = new File(Dir);
        File [] listfile = file.listFiles();

        for (int i = 0; i<listfile.length;i++)
        {
            if(listfile[i].isDirectory())
            {
                pathArray.add(listfile[i].getAbsolutePath());
            }
        }
        return  pathArray;

    }

    public static ArrayList<String> getFilepaths(String Dir)
    {
        ArrayList<String> pathArray = new ArrayList<>();

        File file = new File(Dir);
        File [] listfile = file.listFiles();

        for (int i = 0; i<listfile.length;i++)
        {
            if(listfile[i].isFile())
            {
                pathArray.add(listfile[i].getAbsolutePath());
            }
        }
        return  pathArray;

    }
}
