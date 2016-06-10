package com.example.nikhiljoshi.enlighten.ui.Fragment;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nikhiljoshi.enlighten.R;
import com.example.nikhiljoshi.enlighten.adapter.UsersAdapter;
import com.example.nikhiljoshi.enlighten.data.Contract.EnlightenContract;
import com.example.nikhiljoshi.enlighten.network.MyTwitterApi;
import com.example.nikhiljoshi.enlighten.ui.Activity.MainActivity;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

/**
 * Created by nikhiljoshi on 5/8/16.
 */
public class SelectFriendsFragment extends Fragment {

    private static final String LOG_TAG = SelectFriendsFragment.class.getSimpleName();

    private UsersAdapter mUsersAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_select_friends, container, false);

        mUsersAdapter = new UsersAdapter();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.select_friends_recycler_view);
        mRecyclerView.setAdapter(mUsersAdapter);
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
        api.getFriendsListTake2(mUsersAdapter);

//        api.get(5943622L);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.select_friends_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select_friends: {
                List<User> selectedFriends = mUsersAdapter.getSelectedFriends();
                if (selectedFriends.size() < 1) {
                    Toast.makeText(getContext(), R.string.please_pick_friends, Toast.LENGTH_LONG).show();
                } else {
                    saveFriendsInfoInDb(selectedFriends);
                    goToNextPage();
                }

                return true;
            } default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void goToNextPage() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);

    }

    private void saveFriendsInfoInDb(List<User> users) {
        for (User user: users) {
            ContentValues contentValues = EnlightenContract.FriendEntry.getContentValues(user);
            Uri insertingUri = EnlightenContract.FriendEntry.CONTENT_URI;
            Uri insertedUri = getContext().getContentResolver().insert(insertingUri, contentValues);
            long inserted_row_id = ContentUris.parseId(insertedUri);
            if (inserted_row_id == -1) {
                Log.e(LOG_TAG, "Could not insert info about user: " + user.name);
            }
        }

        ////// Remove the next set of code... just to ensure that info was added to db /////
        long currentSessionUserId = Twitter.getSessionManager().getActiveSession().getUserId();
        Uri uriWithCurrentUserId = EnlightenContract.FriendEntry.buildFriendUriWithCurrentUserId(currentSessionUserId);
        Cursor cursor = getContext().getContentResolver().query(uriWithCurrentUserId, null, null, null, null);

        int numFriendsAdded = 0;
        if (!cursor.moveToFirst()) {
            Log.e(LOG_TAG, "The user hasn't chosen any friends! Weird... he should have chosen some.");
        }

        do {
            numFriendsAdded++;
        } while (cursor.moveToNext());

        Log.i(LOG_TAG, "Added " + numFriendsAdded + " friends to the db! Good job!");
    }
}
