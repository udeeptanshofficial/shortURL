package com.shortURL.URL_Shortener.helpers;

public class DataHelper {
    public static boolean isValidURL(String url){
        String URL_Regex = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";
        return url.matches(URL_Regex);
    }

    public static boolean isValidIP(String ip){
        String IP_Regex = "";
        return ip.matches(IP_Regex);
    }
}
