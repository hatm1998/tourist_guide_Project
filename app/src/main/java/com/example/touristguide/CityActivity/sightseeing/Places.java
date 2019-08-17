package com.example.touristguide.CityActivity.sightseeing;

import android.location.Location;

import com.google.firebase.firestore.GeoPoint;
import com.google.protobuf.DescriptorProtos;

public class Places {

  private   String Name,Image;
 private GeoPoint Location;

    public Places(String name, String image, GeoPoint location) {
        Name = name;
        Image = image;
        Location = location;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public GeoPoint getLocation() {
        return Location;
    }

    public Places()
    {

    }


}
