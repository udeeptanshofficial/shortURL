package com.shortURL.URL_Shortener.URLClasses.models;

import lombok.Data;

@Data
public class ShortURL_ResponseModel {
    public String shortURL;
    public String longURL;
    public boolean isSecure;

    public ShortURL_ResponseModel(String shortURL, String longURL, boolean isSecure) {
        this.shortURL = shortURL;
        this.longURL = longURL;
        this.isSecure = isSecure;
    }
}
