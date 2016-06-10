package com.example.nikhiljoshi.enlighten.ui.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.nikhiljoshi.enlighten.R;
import com.example.nikhiljoshi.enlighten.adapter.FriendAdapter;
import com.example.nikhiljoshi.enlighten.network.pojo.Friend;
import com.example.nikhiljoshi.enlighten.ui.Fragment.ArticlesFragment;
import com.example.nikhiljoshi.enlighten.ui.Fragment.ChosenFriendsFragment;
import com.example.nikhiljoshi.enlighten.ui.Fragment.SelectFriendsFragment;

/**
 * Created by nikhiljoshi on 6/8/16.
 */
public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_articles);

        String userName = getIntent().getStringExtra(FriendAdapter.USER_NAME);
        long userId = getIntent().getLongExtra(FriendAdapter.USER_ID, -1L);

        Bundle args = new Bundle();
        args.putString(FriendAdapter.USER_NAME, userName);
        args.putLong(FriendAdapter.USER_ID, userId);

        ArticlesFragment articlesFragment = new ArticlesFragment();
        articlesFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_articles, articlesFragment)
                .commit();

    }
}
