package com.example.nikhiljoshi.enlighten.network.pojo;

/**
 * Created by nikhiljoshi on 6/7/16.
 */
public class Friend  {

    public String userName;

    public String profileName;

    public String profilePictureUrl;

    public long userId;

    public Friend(String userName, String profileName, String profilePictureUrl, long userId) {
        this.userName = userName;
        this.profileName = profileName;
        this.profilePictureUrl = profilePictureUrl;
        this.userId = userId;
    }


}
