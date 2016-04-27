package com.example.nikhiljoshi.enlighten;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nikhiljoshi on 4/25/16.
 */
public class UtilityTest extends AndroidTestCase {

    public void testDateFormatting() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String realDate = "2016-04-20";
        Date date = sdf.parse(realDate);
        final String formattedDate = Utility.formatDate(date);
        Assert.assertTrue("There is some issue with the date formatting", formattedDate.equals(realDate));


    }
}

