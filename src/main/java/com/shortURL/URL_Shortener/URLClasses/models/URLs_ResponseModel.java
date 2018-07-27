package com.shortURL.URL_Shortener.URLClasses.models;

import lombok.Data;

@Data
public class URLs_ResponseModel {
    public String shortURL;
    public String longURL;
    public boolean isSecure;
    public String secureCode;
    public String startDate;
    public String startTime;
    public String endDate;
    public String endTime;
    public boolean isExisting;
    public int numberOfCharInLongURL;
    public int numberOfCharInShortURL;
}
