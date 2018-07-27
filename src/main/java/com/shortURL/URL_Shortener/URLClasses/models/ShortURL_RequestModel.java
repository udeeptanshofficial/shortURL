package com.shortURL.URL_Shortener.URLClasses.models;

import lombok.Data;

@Data
public class ShortURL_RequestModel {
    public String customURL;
    public String longURL;
    public boolean isSecure;
    public String secureCode;
    public int numberOfDaysValid;
}
