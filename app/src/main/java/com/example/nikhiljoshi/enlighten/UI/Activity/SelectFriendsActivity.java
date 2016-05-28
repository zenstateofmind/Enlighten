package com.example.nikhiljoshi.enlighten.ui.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.nikhiljoshi.enlighten.R;
import com.example.nikhiljoshi.enlighten.ui.Fragment.SelectFriendsFragment;
import com.example.nikhiljoshi.enlighten.ui.Fragment.SelectFriendsInstructionsFragment;

/**
 * As soon as the activity loads, it should show the instructions screen.
 * When the user hits 'OK', this activity should replace this instructions screen
 * with a fragment that allows users to choose their friend's whose
 * tweet articles they are interested in
 */
public class SelectFriendsActivity extends AppCompatActivity implements SelectFriendsInstructionsFragment.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friends);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.select_friends_container,
                        new SelectFriendsInstructionsFragment())
                .commit();
    }

    @Override
    public void onSelected() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.select_friends_container,
                        new SelectFriendsFragment())
                .commit();
    }
}
