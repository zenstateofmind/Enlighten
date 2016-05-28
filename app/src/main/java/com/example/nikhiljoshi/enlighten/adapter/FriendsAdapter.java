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
import com.example.nikhiljoshi.enlighten.network.MyTwitterApi;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for friends that the user has
 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {

    private static final String LOG_TAG = FriendsAdapter.class.getSimpleName();

    private List<User> users;

    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        public ImageView profilePicture;
        public TextView profileName;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            View imageNameFriendItemView = itemView;
            profilePicture = (ImageView) imageNameFriendItemView.findViewById(R.id.profile_picture);
            profileName = (TextView) imageNameFriendItemView.findViewById(R.id.profile_name);
        }

        public void bindView(User user) {
            Picasso.with(profilePicture.getContext()).load(Utility.improveProfileImagePixel(user.profileImageUrl))
                    .into(profilePicture);
            Log.i(LOG_TAG, " Username: " + user.name + " Profile URL:" + user.profileImageUrl);
            profileName.setText(user.name);
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
        if (users.size() > position) {
            User user = users.get(position);
            holder.bindView(user);
        }
    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    public void addUsers(List<User> extraUsers) {
        if (users == null) {
            users = new ArrayList<User>();

        }
        users.addAll(extraUsers);

        notifyDataSetChanged();
    }

    public void swapUsers(List<User> newUsers) {
        if (users == null) {
            users = new ArrayList<User>();

        }
        users.clear();
        users.addAll(newUsers);
        notifyDataSetChanged();
    }
}
