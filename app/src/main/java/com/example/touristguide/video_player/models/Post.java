package com.example.touristguide.video_player.models;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class Post {

    private String  Desc, UserID,CityName;



    private String POSTID;
    private String media_url;
    private ArrayList<String> Categories;
    private java.util.Date Date;
    private GeoPoint loc;


    public Post() {

    }

    public Post(
                String media_url ,
                String CityName,
                String desc,
                String userID,
                ArrayList<String> categories,
                java.util.Date date,
                GeoPoint loc) {
        Date = date;
        Desc = desc;
        Categories = categories;
        this.CityName = CityName;
        this.loc = loc;
        UserID = userID;
        this.media_url = media_url;
    }

    public String getCityName() {
        return CityName;
    }

    public GeoPoint getLoc() {
        return loc;
    }

    public String getPOSTID() {
        return POSTID;
    }

    public void setPOSTID(String POSTID) {
        this.POSTID = POSTID;
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

}










