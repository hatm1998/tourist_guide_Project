package com.example.touristguide.Utilis;

public class GetFireBaseUser {

    public static String  uid ;

    public GetFireBaseUser(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
