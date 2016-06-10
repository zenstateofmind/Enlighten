package com.example.nikhiljoshi.enlighten.data;

import android.net.Uri;
import android.test.AndroidTestCase;

import static com.example.nikhiljoshi.enlighten.data.Contract.EnlightenContract.*;

/**
 * Created by nikhiljoshi on 6/5/16.
 */
public class TestEnlightenContract extends AndroidTestCase {

    private static final long CURRENT_USER_ID = 12345L;
    private static final String USER_NAME = "username";

    private static final String CURRENT_PACK_NAME = "India";
    private static final String PARENT_PACK_NAME = "VC";

    public void testBuildFriendUri() {
        Uri uriWithCurrentUserId = FriendEntry.buildFriendUriWithCurrentUserId(CURRENT_USER_ID);
        assertNotNull("Error: URI shouldn't be null", uriWithCurrentUserId);

        assertEquals("The user session id has changed from the one inserted into the user id",
                CURRENT_USER_ID, FriendEntry.getCurrentUserIdFromUri(uriWithCurrentUserId));


        Uri uriWithCurrentSessionIdAndUserName = FriendEntry.buildUriWithCurrentUserIdAndFriendUserName(CURRENT_USER_ID, USER_NAME);
        assertNotNull("Error, URI shouldn't be null", uriWithCurrentSessionIdAndUserName);

        assertEquals("The user id of the session holder don't match", CURRENT_USER_ID,
                FriendEntry.getCurrentUserIdFromUri(uriWithCurrentSessionIdAndUserName));

        assertEquals("The user name doesn't match", USER_NAME,
                FriendEntry.getFriendUsernameFromUri(uriWithCurrentSessionIdAndUserName));;

    }

    public void testBuildPathUri() {
        Uri uriWithPackName = PackEntry.buildPathUriWithPackName(CURRENT_PACK_NAME);
        assertNotNull("Error: URI shouldn't be null", uriWithPackName);

        Uri uriWithParentPackName = PackEntry.buildPathUriWithParentPackName(PARENT_PACK_NAME);
        assertNotNull("Error: URI shouldn't be null", uriWithParentPackName);

        String packName = PackEntry.getPackNameFromPathNameUri(uriWithPackName);
        assertEquals("Error: pack name has been garbled up", CURRENT_PACK_NAME, packName);

        String parentPackName = PackEntry.getParentPackNameFromPathNameUri(uriWithParentPackName);
        assertEquals("Error: parent pack name has been garbled up", PARENT_PACK_NAME, parentPackName);
    }
}
