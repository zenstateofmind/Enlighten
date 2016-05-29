package com.example.nikhiljoshi.enlighten;

import android.text.TextUtils;

import com.twitter.sdk.android.core.models.Tweet;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        String formattedDay = day < 10 ? "0" + day : day + "";

        String formattedDate = year + "-" + formattedMonth + "-" + formattedDay;
        return formattedDate;
    }

    public static String camelCase(String title) {
        String[] wordsInTitle = TextUtils.split(title, " ");
        String camelCasedTitle = "";

        for (int i = 0; i < wordsInTitle.length; i++) {
            String word = wordsInTitle[i];
            String formattedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
            camelCasedTitle += (i == wordsInTitle.length - 1) ? formattedWord : formattedWord + " ";

        }

        return camelCasedTitle;
    }

    public static String getUrlFromTweet (String tweet) {
        String[] words = TextUtils.split(tweet, " ");
        List<String> urls = new ArrayList<>();
        for (String word: words) {
            if (word.startsWith("https://") || word.startsWith("http://")) {
                urls.add(word);
            }
        }
        return urls.size() > 0 ? urls.get(0) : "";
    }

    public static List<Tweet> filterTweetsWithLink(List<Tweet> tweets) {
        List<Tweet> tweetsWithLink = new ArrayList<>();
        for (Tweet tweet : tweets) {
            // If a certain tweet contains a link, twitter embeds two links into the tweet.
            // For example: To think like this, it helps to have one if these. 😀 @conorsen https://t.co/fmqLc1Zgwb https://t.co/ORtps6PYxg
            if (countNumLinksInTweet(tweet) >= 2) {
                tweetsWithLink.add(tweet);
            }
        }
        return tweetsWithLink;
    }

    /**
     * The first link in a twitter with links is the link that is attached in the tweet
     */
    public static String getLinkFromTweet(Tweet tweet) {

        if (countNumLinksInTweet(tweet) < 2) {
            throw new IllegalAccessError();
        }

        String[] words = TextUtils.split(tweet.text, " ");
        int numLinks = 0;
        String linkFormat = "https://t.co/";
        for (String word : words) {
            if (word.contains(linkFormat)) {
                word = cleanLink(word);
                return word;
            }
        }
        return linkFormat;
    }

    private static String cleanLink(String link) {
        //if link starts with \n, remove it
        if (!link.startsWith("http")) {
            int startIndexOfHttp = link.indexOf("http");
            link = link.substring(startIndexOfHttp);
        }

        // once we remove any garbage before "http" begins, if the link
        // still contains garbage such as
        if (link.contains("\n")) {
            int startOfJunk = link.indexOf("\n");
            link = link.substring(0, startOfJunk);
        }

        return link;
    }

    private static int countNumLinksInTweet(Tweet tweet) {
        String[] words = TextUtils.split(tweet.text, " ");
        int numLinks = 0;
        String linkFormat = "https://t.co/";
        for (String word : words) {
            if (word.contains(linkFormat)) {
                numLinks++;
            }
        }
        return numLinks;
    }

    public static String getPublicationFromUrl(String url) {
        //Separate https or http from the rest of the website
        String[] firstSeparators = TextUtils.split(url, "//");
        String httpAndHttpsRemovedLink = "";
        if(!url.contains("http://") && !url.contains("https://")) {
            httpAndHttpsRemovedLink = firstSeparators[0];
        } else {
            httpAndHttpsRemovedLink = firstSeparators[1];
        }

        String[] publicationWebsiteSeparator = TextUtils.split(httpAndHttpsRemovedLink, "/");
        return publicationWebsiteSeparator[0];
    }

    /**
     * Twitter profile image url that is returned as part of the API is of low pixel quality.
     * The hack to fix this is: remove _normal from the url and the quality improves!
     */
    public static String improveProfileImagePixel(String profileImageUrl){
        return profileImageUrl.replace("_normal.", ".");
    }
}
