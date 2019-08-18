package com.example.touristguide.Utilis;

import android.location.Location;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Date;

public class Post {

    private String Image, Desc, UserID;
    private ArrayList<String> Categories;
    private Date Date;
    public GeoPoint Loc;


    public Post() {

    }

    public Post(String image, String desc, String userID, ArrayList<String> categories, java.util.Date date, GeoPoint location) {
        Image = image;
        Desc = desc;
        UserID = userID;
        Categories = categories;
        Date = date;
        this.Loc = location;
    }

    public String getImage() {
        return Image;
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
        return Loc;
    }
}
