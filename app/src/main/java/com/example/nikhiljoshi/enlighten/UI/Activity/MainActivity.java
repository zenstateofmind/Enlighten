package com.example.nikhiljoshi.enlighten.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.nikhiljoshi.enlighten.R;
import com.example.nikhiljoshi.enlighten.network.pojo.Friend;
import com.example.nikhiljoshi.enlighten.ui.Fragment.ArticlesFragment;
import com.example.nikhiljoshi.enlighten.ui.Fragment.ChosenFriendsFragment;

/**
 * Created by nikhiljoshi on 6/7/16.
 */
public class MainActivity extends AppCompatActivity implements SwapFragments {

    private static String FRAGMENT_TAG = "FRAGMENT_TAG";
    private static String ARTICLE_FRAGMENT = "ARTICLES_FRAGMENT";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_container,
                        new ChosenFriendsFragment(),
                        FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public void swapFragment(Bundle args) {
        ArticlesFragment articlesFragment = new ArticlesFragment();
        articlesFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_container,
                        articlesFragment,
                        FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }
}
