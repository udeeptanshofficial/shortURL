package com.shortURL.URL_Shortener.URLClasses;

import java.math.BigInteger;

public class ShortURLClass {
    private static final String SHORT_URL_CHARACTER_SET =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CHARACTER_SET_LENGTH = SHORT_URL_CHARACTER_SET.length();

    //To produce short URL from a random number generated
    public static String encode(BigInteger number) {
        StringBuilder shortURL = new StringBuilder();
        while ( number.compareTo(new BigInteger("0")) > 0 ) {
            shortURL.append(SHORT_URL_CHARACTER_SET.charAt((number.mod(new BigInteger(String.valueOf(CHARACTER_SET_LENGTH)))).intValue()));
            number = number.divide(new BigInteger(String.valueOf(CHARACTER_SET_LENGTH)));
        }
        return shortURL.reverse().toString();
    }

    //To get back random key value from short URL
    public static BigInteger decode(String shortURL) {
        BigInteger num;
        int power = 0;
        BigInteger decodedNumber = new BigInteger("0");
        for ( int i = shortURL.length()-1; i >= 0; i-- ) {
            num = new BigInteger(String.valueOf(SHORT_URL_CHARACTER_SET.indexOf(shortURL.charAt(i))));
            decodedNumber = decodedNumber.add(getBase62Power(power).multiply(num));

            power++;
        }
        return decodedNumber;
    }

    private static BigInteger getBase62Power(int power){
        BigInteger num = new BigInteger("1");

        if (power==0)
            return num;

        for (int i=0; i<power; i++){
            num = num.multiply(new BigInteger("62"));
        }

        return num;
    }
}

