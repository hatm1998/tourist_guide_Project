package com.example.touristguide.Places_Api;

import android.location.Location;

import com.example.touristguide.Event.Event_Activity.silder_Image.set_Image;

import java.util.ArrayList;

public class set_places_option {

   private String Name,Address,State,Phone,Website;
   private ArrayList<set_Image> Photo ;
    private int Level,user_rating;
    private double Rating;
    private Location location;
    public set_places_option(String name,
                             String address,
                             String state,
                             ArrayList<set_Image> photo,
                             String phone, String website, int level,
                             int user_rating,
                             double rating,
                             Location location) {
        Name = name;
        Address = address;
        State = state;
        Photo = photo;
        Phone = phone;
        Website = website;
        Level = level;
        this.user_rating = user_rating;
        Rating = rating;
        this.location = location;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getState() {
        return State;
    }

    public ArrayList<set_Image> getListPhoto() {
        return Photo;
    }

    public int getLevel() {
        return Level;
    }

    public int getUser_rating() {
        return user_rating;
    }

    public double getRating() {
        return Rating;
    }

    public Location getLocation() {
        return location;
    }

    public String getPhone() {
        return Phone;
    }

    public String getWebsite() {
        return Website;
    }
}
