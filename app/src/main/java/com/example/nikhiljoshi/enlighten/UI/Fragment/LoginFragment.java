package com.example.nikhiljoshi.enlighten.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikhiljoshi.enlighten.R;
import com.example.nikhiljoshi.enlighten.Utility;
import com.example.nikhiljoshi.enlighten.network.MyTwitterApiClient;
import com.example.nikhiljoshi.enlighten.network.pojo.FriendsInfo;
import com.example.nikhiljoshi.enlighten.ui.Activity.MainActivity;
import com.example.nikhiljoshi.enlighten.ui.Activity.SelectFriendsActivity;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.List;

import static com.twitter.sdk.android.tweetui.TweetUtils.*;

/**
 * Created by nikhiljoshi on 5/5/16.
 */
public class LoginFragment extends Fragment {

    private TwitterLoginButton loginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        TwitterSession activeSession = Twitter.getSessionManager().getActiveSession();

//        if (activeSession != null) {
//            Intent intent = new Intent(getContext(), MainActivity.class);
//            startActivity(intent);
//        } else {
            loginButton = (TwitterLoginButton) rootView.findViewById(R.id.twitter_login_button2);
            login();
//        }

        return rootView;
    }

    private void login() {
        Callback<TwitterSession> callback = new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Intent intent = new Intent(getContext(), SelectFriendsActivity.class);
                startActivity(intent);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        };
        loginButton.setCallback(callback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}
