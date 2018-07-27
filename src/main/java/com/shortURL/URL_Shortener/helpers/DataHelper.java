package com.shortURL.URL_Shortener.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataHelper {
    public static final String DATE_PATTERN = "dd/MM/yyyy HH:mm";
    public static final String END_TIME = "23:55";

    public static boolean isValidURL(String url){
        String URL_Regex = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";
        return url.matches(URL_Regex);
    }

    public static boolean isValidIP(String ip){
        String IP_Regex = "";
        return ip.matches(IP_Regex);
    }

    public static String getDateAfter(int days){
        Calendar c=Calendar.getInstance();
        c.add(Calendar.DATE, days);
        Date date = c.getTime();

        DateFormat df = new SimpleDateFormat(DataHelper.DATE_PATTERN);
        String dateString = df.format(date);
        dateString = dateString.split(" ")[0];
        return dateString;
    }

    public static String getDateNow(){
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(DataHelper.DATE_PATTERN);
        String dateString = df.format(date);
        dateString = dateString.split(" ")[0];
        return dateString;

    }

    public static String getTimeNow(){
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(DataHelper.DATE_PATTERN);
        String timeString = df.format(date);
        timeString = timeString.split(" ")[1];
        return timeString;
    }

}
