package com.example.nikhiljoshi.enlighten.network;

import com.example.nikhiljoshi.enlighten.network.pojo.FriendsInfo;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by nikhiljoshi on 5/8/16.
 */
public interface FriendsService {

    @GET("/1.1/friends/list.json")
    void list(@Query("user_id") long user_id, @Query("cursor") long next_cursor, Callback<FriendsInfo> callback);

}
