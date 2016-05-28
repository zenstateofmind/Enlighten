package com.example.nikhiljoshi.enlighten.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikhiljoshi.enlighten.R;
import com.example.nikhiljoshi.enlighten.adapter.FriendsAdapter;
import com.example.nikhiljoshi.enlighten.network.MyTwitterApi;
import com.example.nikhiljoshi.enlighten.network.MyTwitterApiClient;
import com.example.nikhiljoshi.enlighten.network.pojo.FriendsInfo;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by nikhiljoshi on 5/8/16.
 */
public class SelectFriendsFragment extends Fragment {

    private FriendsAdapter mUserFriendsAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select_friends, container, false);

        mUserFriendsAdapter = new FriendsAdapter();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.select_friends_recycler_view);
        mRecyclerView.setAdapter(mUserFriendsAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

//        TwitterSession session = Twitter.getSessionManager().getActiveSession();
//        MyTwitterApiClient apiClient = new MyTwitterApiClient(session);
//        apiClient.getFriendsService().list(session.getUserId(), new Callback<FriendsInfo>() {
//            @Override
//            public void success(Result<FriendsInfo> result) {
//                Toast.makeText(getActivity().getApplicationContext(),
//                        "Number of friends: " + result.data.users.size(), Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void failure(TwitterException e) {
//
//            }
//        });

        MyTwitterApi api = new MyTwitterApi(getActivity().getApplicationContext());
        api.getFriends(mUserFriendsAdapter);

//        api.get(5943622L);

        return rootView;
    }
}
