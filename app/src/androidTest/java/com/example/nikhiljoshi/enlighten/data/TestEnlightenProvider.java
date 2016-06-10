package com.example.nikhiljoshi.enlighten.data;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.example.nikhiljoshi.enlighten.Utility;
import com.example.nikhiljoshi.enlighten.UtilityForTest;
import com.example.nikhiljoshi.enlighten.data.Contract.EnlightenContract;
import com.example.nikhiljoshi.enlighten.data.Provider.EnlightenProvider;

import static com.example.nikhiljoshi.enlighten.data.Contract.EnlightenContract.*;

/**
 * Created by nikhiljoshi on 6/5/16.
 */
public class TestEnlightenProvider extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteRecordsFromDb();
    }

    private void deleteRecordsFromDb() {
        EnlightenDbHelper enlightenDbHelper = new EnlightenDbHelper(mContext);
        SQLiteDatabase db = enlightenDbHelper.getWritableDatabase();
        db.delete(FriendEntry.TABLE_NAME, null, null);
        db.close();
    }

    /**
     * Test to ensure that the EnlightenProvider has been registered -
     * mainly through the android manifest file
     */
    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        ComponentName componentName = new ComponentName(mContext.getPackageName(), EnlightenProvider.class.getName());

        try {
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            assertEquals("The provider has been registered with the authority has " +
                    "was mentioned in the class", providerInfo.authority, EnlightenContract.CONTENT_AUTHORITY);

        } catch (PackageManager.NameNotFoundException e) {
            assertTrue(componentName.getClassName() + " has not been registered", false);
        }

    }

    public void testGetType() {

        long currentSessionUserId = 12345L;
        String userName = "userName";

        String type = mContext.getContentResolver().getType(FriendEntry.buildFriendUriWithCurrentUserId(currentSessionUserId));
        assertEquals("This should return multiple type", FriendEntry.CONTENT_TYPE, type);

        type = mContext.getContentResolver().getType(FriendEntry
                .buildUriWithCurrentUserIdAndFriendUserName(currentSessionUserId, userName));
        assertEquals("This should return individual type", FriendEntry.CONTENT_ITEM_TYPE, type);

    }


    public void testQuery() {

        ContentValues contentValues = UtilityForTest.createSampleFriendData(insertPackData());
        long inserted_id = UtilityForTest.insertSampleData(contentValues, mContext);

        // query with sessionId
        String currentSessionUserId = contentValues.getAsString(FriendEntry.COLUMN_CURRENT_SESSION_USER_ID);
        Uri uriWithCurrentUserId = FriendEntry.buildFriendUriWithCurrentUserId(Long.parseLong(currentSessionUserId));
        Cursor cursor = mContext.getContentResolver().query(uriWithCurrentUserId,
                null, null, null, null);

        assertTrue("Could not get information back", cursor.moveToFirst());

        assertTrue("Results are not accurate", UtilityForTest.validateResults(cursor, contentValues));

        //query with sessionId
        String userName = contentValues.getAsString(FriendEntry.COLUMN_USER_NAME);
        Uri uriWithCurrentUserIdAndFriendUserName =
                FriendEntry.buildUriWithCurrentUserIdAndFriendUserName(Long.parseLong(currentSessionUserId), userName);

        cursor = mContext.getContentResolver().query(uriWithCurrentUserIdAndFriendUserName,
                null, null, null, null);

        assertTrue("Could not get information back", cursor.moveToFirst());

        assertTrue("Results are not accurate", UtilityForTest.validateResults(cursor, contentValues));

    }

    public void testInsert() {

        Uri basicUri = FriendEntry.CONTENT_URI;

        ContentValues contentValues = UtilityForTest.createSampleFriendData(insertPackData());

        Uri insertedUri = mContext.getContentResolver().insert(basicUri, contentValues);

        long inserted_id = ContentUris.parseId(insertedUri);

        assertTrue("Data wasnt inserted properly", inserted_id != -1);
    }

    public void testUpdate() {

        long packRowId = insertPackData();

        ContentValues contentValues = UtilityForTest.createSampleFriendData(packRowId);
        String upatedProfileName = "pmarca";
        String updatedProfilePictureUrl = "https://updated.com";

        long inserted_id = UtilityForTest.insertSampleData(contentValues, mContext);

        contentValues.put(FriendEntry.COLUMN_PROFILE_NAME, upatedProfileName);

        Uri uriWithCurrentUserId =
                FriendEntry.buildFriendUriWithCurrentUserId(contentValues.getAsLong(FriendEntry.COLUMN_CURRENT_SESSION_USER_ID));

        int numRowsUpdated = mContext.getContentResolver().update(uriWithCurrentUserId, contentValues, null, null);

        assertTrue("Row wasn't updated", numRowsUpdated == 1);

        contentValues.put(FriendEntry.COLUMN_PROFILE_PICTURE_URL, updatedProfilePictureUrl);

        Uri uriWithCurrentUserIdAndFriendUserName = FriendEntry.buildUriWithCurrentUserIdAndFriendUserName(
                contentValues.getAsLong(FriendEntry.COLUMN_CURRENT_SESSION_USER_ID),
                contentValues.getAsString(FriendEntry.COLUMN_USER_NAME));

        numRowsUpdated = mContext.getContentResolver().update(uriWithCurrentUserIdAndFriendUserName, contentValues, null, null);

        assertTrue("Row wasn't updated... number of rows that were updated is: " + numRowsUpdated, numRowsUpdated == 1);

    }

    public void testDelete() {

        ContentValues contentValues = UtilityForTest.createSampleFriendData(insertPackData());

        long insertedId = UtilityForTest.insertSampleData(contentValues, mContext);

        Long userSessionId = contentValues.getAsLong(FriendEntry.COLUMN_CURRENT_SESSION_USER_ID);

        Uri uriWithCurrentUserId = FriendEntry.buildFriendUriWithCurrentUserId(userSessionId);
        int numRowsDeleted = mContext.getContentResolver().delete(uriWithCurrentUserId, null, null);

        assertTrue("Rows haven't been deleted... deleted rows: " + numRowsDeleted, numRowsDeleted == 1);

        insertedId = UtilityForTest.insertSampleData(contentValues, mContext);
        String userName = contentValues.getAsString(FriendEntry.COLUMN_USER_NAME);

        Uri uriWithCurrentUserIdAndFriendUserName =
                FriendEntry.buildUriWithCurrentUserIdAndFriendUserName(userSessionId, userName);

        numRowsDeleted = mContext.getContentResolver().delete(uriWithCurrentUserIdAndFriendUserName, null, null);

        assertTrue("Rows haven't been deleted... deleted rows: " + numRowsDeleted, numRowsDeleted == 1);

    }

    private long insertPackData() {
        SQLiteDatabase db = new EnlightenDbHelper(mContext).getWritableDatabase();
        ContentValues packData = UtilityForTest.createSamplePackData();

        return db.insert(PackEntry.TABLE_NAME, null, packData);

    }

}
