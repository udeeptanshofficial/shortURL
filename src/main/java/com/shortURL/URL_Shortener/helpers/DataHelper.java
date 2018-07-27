package com.shortURL.URL_Shortener.helpers;

import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DataHelper {
    public static final String DATE_PATTERN = "dd/MM/yyyy HH:mm";
    public static final String END_TIME = "23:55";

    public static final int RANGE_MIN = 1111111111;
    public static final int RANGE_MAX = 9999999;

    // Returns true if url is valid
    public static boolean isValidURL(String url)
    {
        try {
            new URL(url).toURI();
            return true;
        }

        catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidShortURL(String shortURL){
        return shortURL.matches("[a-zA-Z0-9]*");
    }

    public static String getEncryptedKey(String key) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(key.getBytes());
        return new String(messageDigest.digest());
    }

    public static BigInteger getRandomBigInteger() {
        long min = 20000000L;
        long max = 99999999999L;
        Random r = new Random();
        long number = min + ((long)(r.nextDouble()*(max-min)));

        return BigInteger.valueOf(number);
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
