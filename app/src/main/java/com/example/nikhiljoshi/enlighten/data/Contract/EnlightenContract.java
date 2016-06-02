package com.example.nikhiljoshi.enlighten.data.Contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.twitter.sdk.android.core.internal.TwitterCollection;

/**
 * Created by nikhiljoshi on 6/2/16.
 */
public class EnlightenContract {

    public static final String CONTENT_AUTHORITY = "com.example.nikhiljoshi.enlighten";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FRIEND = "friend";

    public static final class FriendEntry implements BaseColumns {

        //table name
        public static final String TABLE_NAME = "friend";

        // user id of the person who has logged in
        public static final String COLUMN_CURRENT_SESSION_USER_ID = "current_session_user_id";

        public static final String COLUMN_USER_ID = "friend_user_id";

        public static final String COLUMN_USER_NAME = "user_name";

        public static final String COLUMN_PROFILE_NAME = "profile_name";

        public static final String COLUMN_PROFILE_PICTURE_URL = "profile_picture_url";


        ///////// URI Stuff //////////////
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FRIEND)
                .build();

        //// Content Type //////
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY;

        public static Uri buildFriendUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildFriendUriWithCurrentUserId(Long currentSessionUserId) {
            return CONTENT_URI.buildUpon().appendPath(currentSessionUserId + "").build();
        }

        public static Uri buildUriWithCurrentUserIdAndFriendUserName(Long currentSessionUserId,
                                                                     String userName) {
            return CONTENT_URI.buildUpon().appendPath(currentSessionUserId + "")
                    .appendQueryParameter(COLUMN_USER_NAME, userName).build();
        }

        public static Long getCurrentUserIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }

        public static String getFriendUsernameFromUri(Uri uri) {
            String friendUserName = uri.getQueryParameter(COLUMN_USER_NAME);
            if (friendUserName != null) {
                return friendUserName;
            }
            return "";
        }
    }
}
