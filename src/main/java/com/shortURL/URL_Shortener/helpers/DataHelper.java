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

    private static final long RANGE_MIN = 20000000L;
    private static final long RANGE_MAX = 99999999999L;

    public static final String ADMIN_USERNAME_MRIDUL = "mridul";
    public static final String ADMIN_USERNAME_AFSAR = "afsar";
    public static final String ADMIN_PASSWORD_MRIDUL = "qweasdrf";
    public static final String ADMIN_PASSWORD_AFSAR = "afsar!23";


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
        Random r = new Random();
        long number = RANGE_MIN + ((long)(r.nextDouble()*(RANGE_MAX-RANGE_MIN)));

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
