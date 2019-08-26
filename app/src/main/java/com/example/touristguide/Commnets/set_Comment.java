package com.example.touristguide.Commnets;

import java.util.Date;

public class set_Comment {

    private String UserID,comment;
    private Date Date;

    set_Comment(){

    }
    public set_Comment(String UserID, String comment, Date Date) {
        this.UserID = UserID;
        this.comment = comment;
        this.Date = Date;
    }

    public String getUserID() {
        return UserID;
    }

    public String getComment() {
        return comment;
    }

    public java.util.Date getDate() {
        return Date;
    }
}
