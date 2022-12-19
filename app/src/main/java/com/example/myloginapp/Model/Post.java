package com.example.myloginapp.Model;

import java.util.Date;

public class Post extends PostId {

    private String image , user , caption,Type;
    private Date time;
    private double longitude,latitude;

    public String getImage() {
        return image;
    }

    public String getUser() {
        return user;
    }

    public String getCaption() {
        return caption;
    }

    public Date getTime() {
        return time;
    }

    public double getLongitude(){return longitude;}

    public double getLatitude(){return latitude;}
    public String getType(){return Type;}

}
