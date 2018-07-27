package com.shortURL.URL_Shortener.URLClasses;

import java.math.BigInteger;

public class ShortURLClass {
    private static final String SHORT_URL_CHARACTER_SET =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CHARACTER_SET_LENGTH = SHORT_URL_CHARACTER_SET.length();

    //To produce short URL from a random number generated
    public static String encode(long number) {
        StringBuilder shortURL = new StringBuilder();
        while ( number > 0 ) {
            shortURL.append(SHORT_URL_CHARACTER_SET.charAt((int)(number % CHARACTER_SET_LENGTH)));
            number /= CHARACTER_SET_LENGTH;
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

    public static void main(String agr[]){
//        System.out.println(CHARACTER_SET_LENGTH);

        System.out.println(encode(26097696974L));
        System.out.println(encode(233262401942202255L));
        System.out.println(encode(1031894293776L));
//        System.out.println(encode(4112231743382685848569029863052260L));
        System.out.println(encode(8829337178420916741L));


        System.out.println(decode("subham"));
        System.out.println(decode("helloworld"));
        System.out.println(decode("iamfine"));
        System.out.println(decode("mridulmohantripathi"));
        System.out.println(decode("awesomeansh"));


    }
}

