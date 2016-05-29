package com.example.nikhiljoshi.enlighten.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikhiljoshi.enlighten.R;
import com.example.nikhiljoshi.enlighten.data.ArticleInfo;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhiljoshi on 5/24/16.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticlesViewHolder> {

    private static final String LOG_TAG = ArticleAdapter.class.getSimpleName();

    private List<ArticleInfo> articleRelatedTweets;

    @Override
    public ArticlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View articleItemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fragment_article_item,
                parent,
                false
        );

        ArticlesViewHolder articlesViewHolder = new ArticlesViewHolder(articleItemView);
        return articlesViewHolder;
    }

    @Override
    public void onBindViewHolder(ArticlesViewHolder holder, int position) {
        if (articleRelatedTweets.size() > position) {
            ArticleInfo articleInfo = articleRelatedTweets.get(position);
            holder.bindView(articleInfo);
        }
    }

    @Override
    public int getItemCount() {
        return articleRelatedTweets != null ? articleRelatedTweets.size() : 0;
    }

    public static class ArticlesViewHolder extends RecyclerView.ViewHolder {

        public TextView headlineText;
        public TextView publicationText;
        public TextView tweetUserName;

        public ArticlesViewHolder(View itemView) {
            super(itemView);
            View articleItemView = itemView;
            tweetUserName = (TextView) articleItemView.findViewById(R.id.tweet_poster);
            headlineText = (TextView) articleItemView.findViewById(R.id.headlines);
            publicationText = (TextView) articleItemView.findViewById(R.id.publication);
        }

        public void bindView(ArticleInfo articleInfo) {
            headlineText.setText(articleInfo.title);
            publicationText.setText(articleInfo.publication);
            tweetUserName.setText(articleInfo.username);
            Log.i(LOG_TAG, "Binded view for : " + articleInfo.title);
        }
    }

    public void addArticleRelatedTweets(List<ArticleInfo> articleInfos) {
        if (articleRelatedTweets == null) {
            articleRelatedTweets = new ArrayList<>();
        }
        articleRelatedTweets.addAll(articleInfos);
        notifyDataSetChanged();
    }
}
