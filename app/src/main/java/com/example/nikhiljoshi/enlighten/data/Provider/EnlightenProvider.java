package com.example.nikhiljoshi.enlighten.data.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.nikhiljoshi.enlighten.data.EnlightenDbHelper;

import static com.example.nikhiljoshi.enlighten.data.Contract.EnlightenContract.*;

/**
 * Created by nikhiljoshi on 6/2/16.
 */
public class EnlightenProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final int FRIENDS_WITH_CURRENT_USERID = 100;
    private static final int FRIEND_WITH_CURRENT_USERID_AND_USERNAME = 101;
    private static final int FRIEND = 102;

    private EnlightenDbHelper mOpenHelper;


    @Override
    public boolean onCreate() {
        mOpenHelper = new EnlightenDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);

        Cursor cursor;

        switch (match) {
            case FRIENDS_WITH_CURRENT_USERID: {
                Long currentUserId = FriendEntry.getCurrentUserIdFromUri(uri);
                cursor = db.query(FriendEntry.TABLE_NAME,
                        projection,
                        FriendEntry.COLUMN_CURRENT_SESSION_USER_ID + " = ? ",
                        new String[]{currentUserId + ""},
                        sortOrder,
                        null,
                        null);

                break;
            } case FRIEND_WITH_CURRENT_USERID_AND_USERNAME: {
                Long currentUserId = FriendEntry.getCurrentUserIdFromUri(uri);
                String friendUserName = FriendEntry.getFriendUsernameFromUri(uri);

                cursor = db.query(FriendEntry.TABLE_NAME,
                        projection,
                        FriendEntry.COLUMN_CURRENT_SESSION_USER_ID + " = ? AND " +
                                FriendEntry.COLUMN_USER_NAME + " = ?",
                        new String[]{currentUserId + "", friendUserName},
                        sortOrder,
                        null,
                        null);

                break;
            } default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    @Override
    public String getType(Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case FRIENDS_WITH_CURRENT_USERID:
                return FriendEntry.CONTENT_TYPE;
            case FRIEND_WITH_CURRENT_USERID_AND_USERNAME:
                return FriendEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Uri: " + uri + " doesnt have a type");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match) {
            case FRIEND: {
                long id = db.insert(FriendEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = FriendEntry.buildFriendUriWithInsertedRowId(id);
                } else {
                    throw new android.database.SQLException("Failed to insert row for: " + uri);
                }
                break;

            } default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numRowsDeleted = 0;

        switch (match) {
            case FRIENDS_WITH_CURRENT_USERID : {
                Long currentUserId = FriendEntry.getCurrentUserIdFromUri(uri);
                numRowsDeleted = db.delete(FriendEntry.TABLE_NAME,
                        FriendEntry.COLUMN_CURRENT_SESSION_USER_ID + " = ?",
                        new String[]{currentUserId + ""});
                break;
            } case FRIEND_WITH_CURRENT_USERID_AND_USERNAME : {
                Long currentUserId = FriendEntry.getCurrentUserIdFromUri(uri);
                String friendUserName = FriendEntry.getFriendUsernameFromUri(uri);
                numRowsDeleted = db.delete(FriendEntry.TABLE_NAME,
                        FriendEntry.COLUMN_CURRENT_SESSION_USER_ID + " = ? AND " +
                                FriendEntry.COLUMN_USER_NAME + " = ? ",
                        new String[]{currentUserId + "", friendUserName});
                break;
            } default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (numRowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numRowsUpdated = 0;

        switch (match) {
            case FRIENDS_WITH_CURRENT_USERID : {
                Long currentUserId = FriendEntry.getCurrentUserIdFromUri(uri);
                numRowsUpdated = db.update(FriendEntry.TABLE_NAME,
                        values,
                        FriendEntry.COLUMN_CURRENT_SESSION_USER_ID + " = ?",
                        new String[]{currentUserId + ""});
                break;
            } case FRIEND_WITH_CURRENT_USERID_AND_USERNAME : {
                Long currentUserId = FriendEntry.getCurrentUserIdFromUri(uri);
                String friendUserName = FriendEntry.getFriendUsernameFromUri(uri);
                numRowsUpdated = db.update(FriendEntry.TABLE_NAME,
                        values,
                        FriendEntry.COLUMN_CURRENT_SESSION_USER_ID + " = ? AND " +
                                FriendEntry.COLUMN_USER_NAME + " = ? ",
                        new String[]{currentUserId + "", friendUserName});
                break;
            } default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (numRowsUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsUpdated;

    }

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_FRIEND, FRIEND);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_FRIEND + "/#" , FRIENDS_WITH_CURRENT_USERID);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_FRIEND + "/#/*", FRIEND_WITH_CURRENT_USERID_AND_USERNAME);
        return uriMatcher;
    }

}
