package com.example.touristguide.notification_saved;

import java.util.Date;

public class setNotification {

    private String img,subject,document;
    private Date date;

   public  setNotification(String IMG , String Subject  ,String Document , Date Date){
        this.img=IMG;
        this.subject=Subject;
        this.document=Document;
        this.date=Date;
    }

    public String getImg() {
        return img;
    }

    public String geSubject() {
        return subject;
    }

    public String getDocument() {
        return document;
    }

    public Date getDate() {
        return date;
    }
}
