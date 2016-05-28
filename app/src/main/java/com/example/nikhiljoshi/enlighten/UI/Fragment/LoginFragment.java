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

        loginButton = (TwitterLoginButton) rootView.findViewById(R.id.twitter_login_button2);
        Callback<TwitterSession> callback = new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
//                // TODO: Remove toast and use the TwitterSession's userID
                Intent intent = new Intent(getContext(), SelectFriendsActivity.class);
                startActivity(intent);
                /**
                 * Once the user has successfully signed in, direct them to the selection page,
                 * where they can select the users whose articles they want to read
                 */

//                // with your app's user model
//                String msg = "@" + session.getUserName() + " logged in! (#" + result.data.getUserName() + ")";
////                Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
//                Log.v("TwitterKit-Success", msg);
//
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        };
        loginButton.setCallback(callback);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}
