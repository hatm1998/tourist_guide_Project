package com.example.touristguide.Utilis;

import java.util.Date;

public class Post  {

    String UserID , Desc , Image ;
    Date date;

    public Post()
    {

    }

    public String getUserID() {
        return UserID;
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

    public Post(String userID, String desc, String image, Date date) {

        UserID = userID;
        Desc = desc;
        Image = image;
        this.date = date;
    }
}
