package com.example.touristguide.notification_saved;

import java.util.Date;

public class setNotification {

    private String Msg, PostInfo, UserID, UserSender;
    private Date date;

    setNotification() {

    }

    public setNotification(String msg, String postInfo, String userID, String userSender, Date date) {
        Msg = msg;
        PostInfo = postInfo;
        UserID = userID;
        UserSender = userSender;
        this.date = date;
    }

    public String getMsg() {
        return Msg;
    }

    public String getPostInfo() {
        return PostInfo;
    }

    public String getUserID() {
        return UserID;
    }

    public String getUserSender() {
        return UserSender;
    }

    public Date getDate() {
        return date;
    }
}
