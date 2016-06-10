package com.example.nikhiljoshi.enlighten.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.nikhiljoshi.enlighten.data.Contract.EnlightenContract.*;

import com.example.nikhiljoshi.enlighten.data.Contract.EnlightenContract;
import com.example.nikhiljoshi.enlighten.network.pojo.Friend;

/**
 * Created by nikhiljoshi on 6/3/16.
 */
public class EnlightenDbHelper extends SQLiteOpenHelper {

    //TODO: Upgrade the database version to the next level. Fill out onUpgrade
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "enlighten.db";

    public EnlightenDbHelper(Context context) {
        this(context, DATABASE_VERSION);
    }

    EnlightenDbHelper(Context context, int databaseVersion) {
        super(context, DATABASE_NAME, null, databaseVersion);

    }

    // create the new table
    final String SQL_CREATE_FRIEND_TABLE_2 = "CREATE TABLE " + FriendEntry.TABLE_NAME + "(" +
            FriendEntry._ID + " INTEGER PRIMARY KEY, " +
            FriendEntry.COLUMN_CURRENT_SESSION_USER_ID + " INTEGER NOT NULL, " +
            FriendEntry.COLUMN_USER_ID + " INTEGER NOT NULL, " +
            FriendEntry.COLUMN_USER_NAME + " STRING NOT NULL, " +
            FriendEntry.COLUMN_PROFILE_NAME + " STRING NOT NULL, " +
            FriendEntry.COLUMN_PROFILE_PICTURE_URL + " STRING NOT NULL, " +
            FriendEntry.COLUMN_PACK_KEY + " INTEGER, " +
            " FOREIGN KEY (" + FriendEntry.COLUMN_PACK_KEY + ") REFERENCES " +
            PackEntry.TABLE_NAME + "(" + PackEntry._ID + "), " +
            " UNIQUE (" + FriendEntry.COLUMN_CURRENT_SESSION_USER_ID + ", " +
            FriendEntry.COLUMN_USER_ID + ") ON CONFLICT REPLACE" +
            ");";

    final String SQL_CREATE_PACK_TABLE_2 = "CREATE TABLE " + PackEntry.TABLE_NAME + "(" +
            PackEntry._ID + " INTEGER PRIMARY KEY, " +
            PackEntry.COLUMN_PACK_NAME + " STRING NOT NULL, " +
            PackEntry.COLUMN_PACK_PARENT_NAME + " STRING, " +
            PackEntry.COLUMN_DESCRIPTION + " STRING NOT NULL" +
            ");";

    // copy data from the old table to the new table



    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FRIEND_TABLE = "CREATE TABLE " + FriendEntry.TABLE_NAME + "(" +
                FriendEntry._ID + " INTEGER PRIMARY KEY, " +
                FriendEntry.COLUMN_CURRENT_SESSION_USER_ID + " INTEGER NOT NULL, " +
                FriendEntry.COLUMN_USER_ID + " INTEGER NOT NULL, " +
                FriendEntry.COLUMN_USER_NAME + " STRING NOT NULL, " +
                FriendEntry.COLUMN_PROFILE_NAME + " STRING NOT NULL, " +
                FriendEntry.COLUMN_PROFILE_PICTURE_URL + " STRING NOT NULL" +
                ");";


        db.execSQL(SQL_CREATE_FRIEND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            String tempFriendTableName = FriendEntry.TABLE_NAME + "_TEMP";
            String alterFamilyTableSql = "ALTER TABLE " + FriendEntry.TABLE_NAME + " RENAME TO " + tempFriendTableName;
            String copyDataFromOldFriendTableToNew =
                    "INSERT INTO " + FriendEntry.TABLE_NAME + " (" +
                            FriendEntry.COLUMN_CURRENT_SESSION_USER_ID + ", " +
                            FriendEntry.COLUMN_USER_ID + ", " +
                            FriendEntry.COLUMN_USER_NAME + ", " +
                            FriendEntry.COLUMN_PROFILE_PICTURE_URL + ", " +
                            FriendEntry.COLUMN_PROFILE_NAME + ") " +
                            " SELECT " +
                                FriendEntry.COLUMN_CURRENT_SESSION_USER_ID + " " + FriendEntry.COLUMN_CURRENT_SESSION_USER_ID + ", " +
                                FriendEntry.COLUMN_USER_ID + " " + FriendEntry.COLUMN_USER_ID + ", " +
                                FriendEntry.COLUMN_USER_NAME + " " + FriendEntry.COLUMN_USER_NAME +  ", " +
                                FriendEntry.COLUMN_PROFILE_PICTURE_URL + " " + FriendEntry.COLUMN_PROFILE_PICTURE_URL + ", " +
                                FriendEntry.COLUMN_PROFILE_NAME + " " + FriendEntry.COLUMN_PROFILE_NAME + " FROM " + tempFriendTableName;
            String drop_table = "DROP TABLE " + tempFriendTableName;

            db.execSQL(alterFamilyTableSql);
            db.execSQL(SQL_CREATE_PACK_TABLE_2);
            db.execSQL(SQL_CREATE_FRIEND_TABLE_2);
            db.execSQL(copyDataFromOldFriendTableToNew);
            db.execSQL(drop_table);
        }
    }
}
