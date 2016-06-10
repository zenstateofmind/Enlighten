package com.example.nikhiljoshi.enlighten.ui.Fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nikhiljoshi.enlighten.R;
import com.example.nikhiljoshi.enlighten.adapter.FriendAdapter;
import com.example.nikhiljoshi.enlighten.data.Contract.EnlightenContract;
import com.example.nikhiljoshi.enlighten.network.pojo.Friend;
import com.example.nikhiljoshi.enlighten.ui.Activity.SwapFragments;
import com.twitter.sdk.android.Twitter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhiljoshi on 6/7/16.
 */
public class ChosenFriendsFragment extends Fragment {

    private static final String LOG_TAG = ChosenFriendsFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private FriendAdapter mFriendAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chosen_friends, container, false);

        mFriendAdapter = new FriendAdapter(getActivity());

        loadFriendsFromDb();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.chosen_friends_recyclerView);
        mRecyclerView.setAdapter(mFriendAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return rootView;
    }

    private void loadFriendsFromDb() {

        List<Friend> friends = new ArrayList<>();
        long currentSessionUserId = Twitter.getSessionManager().getActiveSession().getUserId();
        Uri uriWithCurrentUserId = EnlightenContract.FriendEntry.buildFriendUriWithCurrentUserId(currentSessionUserId);

        //@NonNull Uri uri, @Nullable String[] projection,
        //@Nullable String selection, @Nullable String[] selectionArgs,
        //@Nullable String sortOrder
        Cursor cursor = getContext().getContentResolver().query(uriWithCurrentUserId, null, null, null, null);

        if (!cursor.moveToFirst()) {
            Log.e(LOG_TAG, "The user hasn't chosen any friends! Weird... he should have chosen some.");
        }

        do {
            friends.add(EnlightenContract.FriendEntry.convertToFriend(cursor));
        } while (cursor.moveToNext());

        mFriendAdapter.addFriends(friends);

    }

    public boolean goHome() {
        return true;
    }



}
