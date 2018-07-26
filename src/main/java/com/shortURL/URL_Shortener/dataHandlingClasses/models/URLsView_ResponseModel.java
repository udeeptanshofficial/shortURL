package com.shortURL.URL_Shortener.dataHandlingClasses.models;

import lombok.Data;

import java.util.List;

@Data
public class URLsView_ResponseModel {
    public List<URLs_ResponseModel> existing;
    public List<URLs_ResponseModel> expired;
}
