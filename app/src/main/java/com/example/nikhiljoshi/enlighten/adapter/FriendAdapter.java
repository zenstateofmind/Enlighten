package com.example.nikhiljoshi.enlighten.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikhiljoshi.enlighten.R;
import com.example.nikhiljoshi.enlighten.Utility;
import com.example.nikhiljoshi.enlighten.network.pojo.Friend;
import com.example.nikhiljoshi.enlighten.ui.Activity.ArticleActivity;
import com.example.nikhiljoshi.enlighten.ui.Activity.SwapFragments;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhiljoshi on 6/7/16.
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    private List<Friend> friends;
    private Context mContext;

    public FriendAdapter(Context context) {
        mContext = context;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View friendItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_image_username_item, parent, false);

        FriendViewHolder viewHolder = new FriendViewHolder(friendItemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        if (friends.size() > position) {
            Friend friend = friends.get(position);
            holder.bindView(friend);
        }
    }

    @Override
    public int getItemCount() {
        return friends == null ? 0 : friends.size();
    }

    public void addFriends(List<Friend> addedFriends) {
        if (friends == null) {
            friends = new ArrayList<>();
        }
        friends.addAll(addedFriends);
        notifyDataSetChanged();
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mProfilePicture;
        public TextView mProfileName;
        public Friend friend;

        public FriendViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mProfilePicture = (ImageView) itemView.findViewById(R.id.profile_picture);
            mProfileName = (TextView) itemView.findViewById(R.id.profile_name);
        }

        public void bindView(Friend friend) {
            this.friend = friend;
            Picasso.with(mProfilePicture.getContext()).load(Utility.improveProfileImagePixel(friend.profilePictureUrl))
                    .into(mProfilePicture);
            mProfileName.setText(friend.profileName);
        }

        @Override
        public void onClick(View itemView) {

            Intent intent = new Intent(mContext, ArticleActivity.class);
            intent.putExtra(USER_NAME, friend.userName);
            intent.putExtra(USER_ID, friend.userId);
            mContext.startActivity(intent);
//
//            Bundle args = new Bundle();
//            args.putString(USER_NAME, friend.userName);
//            args.putLong(USER_ID, friend.userId);
//            activity.swapFragment(args);

        }
    }
}
