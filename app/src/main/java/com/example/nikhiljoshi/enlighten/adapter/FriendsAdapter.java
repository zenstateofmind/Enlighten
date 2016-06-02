package com.example.nikhiljoshi.enlighten.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikhiljoshi.enlighten.R;
import com.example.nikhiljoshi.enlighten.Utility;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for friends that the user has
 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {

    private static final String LOG_TAG = FriendsAdapter.class.getSimpleName();

    private List<User> friends;
    private List<User> selectedFriends = new ArrayList<>();

    /**
     * This is the view holder for the FriendsAdapter
     */
    public class FriendsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView profilePicture;
        public TextView profileName;
        public User user;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            View imageNameFriendItemView = itemView;
            profilePicture = (ImageView) imageNameFriendItemView.findViewById(R.id.profile_picture);
            profileName = (TextView) imageNameFriendItemView.findViewById(R.id.profile_name);
        }

        /**
         * We bind the user to the view. In here, we also handle the view settings to that
         * of a selected user or a non selected user. We first check if the user present in this view
         * has been selected by the AppUser to be a part of friends list that they want to get tweets from
         */
        public void bindView(User user) {
            View rootView = profilePicture.getRootView();
            if (FriendsAdapter.this.isSelectedUser(user)) {
                setViewSettingsToSelectedUser(rootView);
            } else {
                setViewSettingsToNonSelectedUser(rootView);
            }
            Picasso.with(profilePicture.getContext()).load(Utility.improveProfileImagePixel(user.profileImageUrl))
                    .into(profilePicture);
            Log.i(LOG_TAG, " Username: " + user.name + " Profile URL:" + user.profileImageUrl);
            profileName.setText(user.name);
            this.user = user;
        }


        /**
         * When the AppUser clicks on a twitter user that she wants to include in their
         * reading list, we do that... and when they click on the user again, they get deselected
         */
        @Override
        public void onClick(View itemView) {
            if (!FriendsAdapter.this.isSelectedUser(user)) {
                setViewSettingsToSelectedUser(itemView);
                FriendsAdapter.this.addToSelectedFriends(user);
            } else {
                setViewSettingsToNonSelectedUser(itemView);
                FriendsAdapter.this.removeFromSelectedFriends(user);
            }

        }

        private void setViewSettingsToSelectedUser(View itemView) {
            itemView.setBackgroundResource(R.color.cardview_dark_background);
        }

        private void setViewSettingsToNonSelectedUser(View itemView) {
            itemView.setBackgroundResource(R.color.default_background);
        }
    }

    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View friendImageNameItemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.friend_image_username_item, parent, false
        );

        FriendsViewHolder viewHolder = new FriendsViewHolder(friendImageNameItemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FriendsViewHolder holder, int position) {
        if (friends.size() > position) {
            User user = friends.get(position);
            holder.bindView(user);
        }
    }

    @Override
    public int getItemCount() {
        return friends == null ? 0 : friends.size();
    }

    public void addUsers(List<User> extraUsers) {
        if (friends == null) {
            friends = new ArrayList<User>();

        }
//        friends.addAll(extraUsers);
        friends.addAll(0, extraUsers);

        notifyDataSetChanged();
    }

    public void swapUsers(List<User> newUsers) {
        if (friends == null) {
            friends = new ArrayList<User>();

        }
        friends.clear();
        friends.addAll(newUsers);
        notifyDataSetChanged();
    }

    public List<User> getSelectedFriends() {
        return selectedFriends;
    }

    private void addToSelectedFriends(User user) {
        selectedFriends.add(user);
    }

    private void removeFromSelectedFriends(User user) {
        selectedFriends.remove(user);
    }

    private boolean isSelectedUser(User user) {
        return selectedFriends.contains(user) ? true : false;
    }
}
