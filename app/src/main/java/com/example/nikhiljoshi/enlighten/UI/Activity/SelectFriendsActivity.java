package com.example.nikhiljoshi.enlighten.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.nikhiljoshi.enlighten.R;
import com.example.nikhiljoshi.enlighten.ui.Fragment.ChosenFriendsFragment;
import com.example.nikhiljoshi.enlighten.ui.Fragment.SelectFriendsFragment;
import com.example.nikhiljoshi.enlighten.ui.Fragment.SelectFriendsInstructionsFragment;

import static com.example.nikhiljoshi.enlighten.ui.Fragment.SelectFriendsFragment.*;
import static com.example.nikhiljoshi.enlighten.ui.Activity.MainActivity.*;

/**
 * As soon as the activity loads, it should show the instructions screen.
 * When the user hits 'OK', this activity should replace this instructions screen
 * with a fragment that allows users to choose their friend's whose
 * tweet articles they are interested in
 */
public class SelectFriendsActivity extends AppCompatActivity implements SelectFriendsInstructionsFragment.Callback {

    public static final String SHOWING_FIRST_TIME_TAG = "first_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friends);

        final Intent intent = getIntent();

        /**
         * The next two set of extras are used in SelectFriendsFragment.
         * The first extra - used to figure out which activity to launch once you select your friends
         * The second extra - The source from where the UsersAdapter loads its friends - whether
         *      it is through the database or through the API
         */
        final ActivityToStartOnFriendSelection activityToStartEnum =
                (ActivityToStartOnFriendSelection) intent.getSerializableExtra(ACTIVITY_TO_START_ON_FRIENDS_SELECTION_TAG);
        final FriendSource friendSourceEnum = (FriendSource) intent.getSerializableExtra(FRIEND_SOURCE_FOR_ADDING_NEW_FRIENDS_TAG);
        final long packId = intent.getLongExtra(ChosenFriendsFragment.PACK_ID_TAG, NO_PACK);

        if (activityToStartEnum != null && friendSourceEnum != null) {
            launchSelectFriendsFragment(activityToStartEnum, friendSourceEnum, packId);
        } else {
            /**
             * If this activity is loading for the first time, display the fragment that instructs the
             * user on what to do with this activity
             */
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.select_friends_container,
                            new SelectFriendsInstructionsFragment())
                    .commit();

        }

    }

    @Override
    public void onSelected() {
        launchSelectFriendsFragment(ActivityToStartOnFriendSelection.MAIN_ACTIVITY,
                                    FriendSource.API, NO_PACK);
    }

    private void launchSelectFriendsFragment(ActivityToStartOnFriendSelection activityToStartOnFriendSelectionEnum,
                                             FriendSource friendsSourceEnum, Long packId) {
        Bundle arguments = new Bundle();

        arguments.putSerializable(ACTIVITY_TO_START_ON_FRIENDS_SELECTION_TAG, activityToStartOnFriendSelectionEnum);
        arguments.putSerializable(FRIEND_SOURCE_FOR_ADDING_NEW_FRIENDS_TAG, friendsSourceEnum);
        arguments.putLong(ChosenFriendsFragment.PACK_ID_TAG, packId);

        SelectFriendsFragment fragment = new SelectFriendsFragment();
        fragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.select_friends_container, fragment)
                .commit();

    }
}
