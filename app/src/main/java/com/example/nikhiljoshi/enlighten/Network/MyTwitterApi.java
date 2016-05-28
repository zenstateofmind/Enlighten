package com.example.nikhiljoshi.enlighten.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.nikhiljoshi.enlighten.Utility;
import com.example.nikhiljoshi.enlighten.adapter.ArticleAdapter;
import com.example.nikhiljoshi.enlighten.adapter.FriendsAdapter;
import com.example.nikhiljoshi.enlighten.network.pojo.FriendsInfo;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhiljoshi on 5/8/16.
 */
public class MyTwitterApi {

    public static final String LOG = MyTwitterApi.class.getSimpleName();
    public final TwitterSession twitterSession;
    public final MyTwitterApiClient myTwitterApiClient;
    public final TwitterApiClient twitterApiClient;
    private final Context context;

    public MyTwitterApi(Context context) {
        this.context = context;
        twitterSession = Twitter.getSessionManager().getActiveSession();
        myTwitterApiClient = new MyTwitterApiClient(twitterSession);
        twitterApiClient = Twitter.getApiClient();
    }

    public void getFriends(FriendsAdapter friendsAdapter) {
        final List<User> friends = new ArrayList<>();
        getFriendsList(friendsAdapter, -1L, 2);
    }

    private void getFriendsList( final FriendsAdapter friendsAdapter, long next_cursor, final int countDown) {
        myTwitterApiClient.getFriendsService().list(twitterSession.getUserId(), next_cursor, new Callback<FriendsInfo>() {
            @Override
            public void success(Result<FriendsInfo> result) {
                List<User> friends = result.data.users;
                for (User friend : friends) {
                    getUserTweetsWithLinks(friend.id, friend.name);
                }
                friendsAdapter.addUsers(friends);
                if (countDown > 0) {
                    getFriendsList(friendsAdapter, result.data.nextCursor, countDown - 1);
                    Log.i(LOG, "Next cursor: " + result.data.nextCursor);
                }
                Toast.makeText(context, "Number of friends: " + friends.size(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(TwitterException e) {
                Log.d(LOG, "Failed in getting friend's list " + e);
            }
        });
    }

    public void getUserTweetsWithLinks(final Long userId, final String userName) {
        twitterApiClient.getStatusesService().userTimeline(userId, null, 200, null, null,
                null, null, null, null, new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        List<Tweet> tweets = Utility.filterTweetsWithLink(result.data);
                        Log.i(LOG, "Number of tweets by " + userName + " is as follows: " + tweets.size());
                    }

                    @Override
                    public void failure(TwitterException e) {
                        Log.d(LOG, "Failed in getting user's timelines: " + e);
                    }
                });
    }
}
