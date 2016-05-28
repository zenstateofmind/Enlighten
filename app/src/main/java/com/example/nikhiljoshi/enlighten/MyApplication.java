package com.example.nikhiljoshi.enlighten;

import android.app.Application;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by nikhiljoshi on 5/8/16.
 */
public class MyApplication extends Application {

    /**
     * This is where {@link Twitter} gets instantiated.
     * The way {@link Fabric} works is as follows: <br>
     *     Each
     */
    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(
                BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);

        Fabric.with(this, new Twitter(authConfig));
    }
}
