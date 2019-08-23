package com.example.touristguide.Commnets;

import java.util.Date;

public class set_Comment {

    private String Img_User,message;
    private Date date;

    public set_Comment(String img_User, String message, Date date) {
        Img_User = img_User;
        this.message = message;
        this.date = date;
    }

    public String getImg_User() {
        return Img_User;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}
