package com.example.touristguide.Event.Event_Activity;

import java.util.Date;

public class set_Events {

    private  String Img,Name_ev;
    private Date date;

    public set_Events(String img, String name_ev, Date date) {
        Img = img;
        Name_ev = name_ev;
        this.date = date;
    }

    public String getImg() {
        return Img;
    }

    public String getName_ev() {
        return Name_ev;
    }

    public Date getDate() {
        return date;
    }
}
