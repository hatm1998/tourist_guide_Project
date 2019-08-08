package com.example.touristguide.Profile.Fragment_Post_inProfile;

import android.location.Location;

import java.util.Date;

public class setPost {

    private String key,pic_user,yourname,img_post,document;
    private Date date;
    private Location location;

    public setPost(String key,String pic_user, String yourname, String img_post, String document, Date date, Location location) {
       this.key=key;
        this.pic_user = pic_user;
        this.yourname = yourname;
        this.img_post = img_post;
        this.document = document;
        this.date = date;
        this.location = location;
    }

    public String getKey() {
        return key;
    }

    public String getPic_user() {
        return pic_user;
    }

    public String getYourname() {
        return yourname;
    }

    public String getImg_post() {
        return img_post;
    }

    public String getDocument() {
        return document;
    }

    public Date getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }
}
