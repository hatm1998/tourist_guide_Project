package com.example.touristguide.Places_Api;

import android.location.Location;

public class set_places_option {

   private String Name,Address,State,Photo;
    private int Level,user_rating;
    private double Rating;
    private Location location;

    public set_places_option(String name,
                             String address,
                             String state,
                             String photo,
                             int level,
                             int user_rating,
                             double rating,
                             Location location) {
        Name = name;
        Address = address;
        State = state;
        Photo = photo;
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

    public String getPhoto() {
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
}
