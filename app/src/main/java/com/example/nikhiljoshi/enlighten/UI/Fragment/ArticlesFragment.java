package com.example.nikhiljoshi.enlighten.ui.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nikhiljoshi.enlighten.R;
import com.example.nikhiljoshi.enlighten.adapter.ArticleAdapter;
import com.example.nikhiljoshi.enlighten.network.MyTwitterApi;
import com.twitter.sdk.android.tweetui.TweetViewFetchAdapter;

/**
 * Created by nikhiljoshi on 5/28/16.
 */
public class ArticlesFragment extends Fragment {

    private ArticleAdapter mArticleAdapter;

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_articles_view, container, false);

        mArticleAdapter = new ArticleAdapter();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.articles_recycle_view);
        mRecyclerView.setAdapter(mArticleAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MyTwitterApi api = new MyTwitterApi(getActivity().getApplicationContext());
        api.getUserTweetsWithLinks(5943622L, "pmarca", mArticleAdapter);


        return rootView;
    }
}