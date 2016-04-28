package com.example.nikhiljoshi.enlighten;

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nikhiljoshi on 4/25/16.
 */
@RunWith(AndroidJUnit4.class)
public class TestUtility extends InstrumentationTestCase {

    @Test
    public void testDateFormattingWithMonthLessThan10() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String realDate = "2016-04-20";
        Date date = sdf.parse(realDate);
        final String formattedDate = Utility.formatDate(date);
        Assert.assertTrue("There is some issue with the date formatting", formattedDate.equals(realDate));
    }

    @Test
    public void testDateFormattingWithDayLessThan10() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String realDate = "2016-04-01";
        Date date = sdf.parse(realDate);
        final String formattedDate = Utility.formatDate(date);
        Assert.assertTrue("There is some issue with the date formatting", formattedDate.equals(realDate));
    }
}

