package com.example.touristguide.video_player.models;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class Post {

    private String  Desc, UserID;
    private String media_url;
    private ArrayList<String> Categories;
    private java.util.Date Date;
    public GeoPoint loc;


    public Post() {

    }

    public Post(
                String media_url ,
                String desc,
                String userID,
                ArrayList<String> categories,
                java.util.Date date,
                GeoPoint location) {
        Desc = desc;
        UserID = userID;
        Categories = categories;
        Date = date;
        this.loc = location;
        this.media_url = media_url;
    }


    public String getMedia_url() {
        return media_url;
    }

    public String getDesc() {
        return Desc;
    }

    public String getUserID() {
        return UserID;
    }

    public ArrayList<String> getCategories() {
        return Categories;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public GeoPoint getLocation() {
        return loc;
    }
}










