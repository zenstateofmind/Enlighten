package com.example.nikhiljoshi.enlighten;

import android.text.TextUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by nikhiljoshi on 4/25/16.
 */
public class Utility {

    public static String formatDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        //calendar time sets jan to 0
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        String formattedMonth = month < 10 ? "0" + month : month + "";

        String formattedDate = year + "-" + formattedMonth + "-" + day;
        return formattedDate;
    }
}
