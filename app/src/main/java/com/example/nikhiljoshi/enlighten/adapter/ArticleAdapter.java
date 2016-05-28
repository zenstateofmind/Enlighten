package com.example.nikhiljoshi.enlighten.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhiljoshi on 5/24/16.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticlesViewHolder> {

    private static final String LOG_TAG = ArticleAdapter.class.getSimpleName();

    private List<Tweet> articleRelatedTweets;

    @Override
    public ArticlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ArticlesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return articleRelatedTweets != null ? articleRelatedTweets.size() : 0;
    }

    public static class ArticlesViewHolder extends RecyclerView.ViewHolder {

        public ArticlesViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void addArticleRelatedTweets(List<Tweet> tweets) {
        if (articleRelatedTweets == null) {
            articleRelatedTweets = new ArrayList<>();
        }
        articleRelatedTweets.addAll(tweets);
    }
}
