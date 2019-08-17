package com.example.touristguide.Profile.Fragment_Post_inProfile;

import android.location.Geocoder;
import android.location.Location;

import java.util.Date;

public class setPost {

    private  String []  Categories;
    private String User_ID,Desc,Image;
    private Date date;
    private Geocoder location;

    public setPost()
    {

    }

    public setPost(String[] categories, String user_ID, String desc, String image, Date date, Geocoder location) {
        Categories = categories;
        User_ID = user_ID;
        Desc = desc;
        Image = image;
        this.date = date;
        this.location = location;
    }

    public String[] getCategories() {
        return Categories;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public String getDesc() {
        return Desc;
    }

    public String getImage() {
        return Image;
    }

    public Date getDate() {
        return date;
    }

    public Geocoder getLocation() {
        return location;
    }
}
