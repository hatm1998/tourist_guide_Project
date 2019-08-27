package com.example.touristguide.CityActivity.sightseeing;

import android.location.Location;

import com.google.firebase.firestore.GeoPoint;
import com.google.protobuf.DescriptorProtos;

public class Places {

  private   String ID_Place,Name,Image;
  private Location location;

    public Places(String ID_Place ,String name, String image ,Location location) {
        this.ID_Place=ID_Place;
        Name = name;
       this.Image=image;
        this.location=location;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public String getID_Place() {
        return ID_Place;
    }

    public Location getLocation() {
        return location;
    }
}
