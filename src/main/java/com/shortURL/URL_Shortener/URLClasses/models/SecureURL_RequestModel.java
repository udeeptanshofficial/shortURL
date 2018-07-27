package com.shortURL.URL_Shortener.URLClasses.models;

import lombok.Data;

@Data
public class SecureURL_RequestModel {
    public String shortURL;
    public String securityKey;
}
