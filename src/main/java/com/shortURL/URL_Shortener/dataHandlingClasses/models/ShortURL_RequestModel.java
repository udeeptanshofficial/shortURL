package com.shortURL.URL_Shortener.dataHandlingClasses.models;

import lombok.Data;

@Data
public class ShortURL_RequestModel {
    public String customURL;
    public String longURL;
    public boolean isSecure;
    public String secureCode;
}
