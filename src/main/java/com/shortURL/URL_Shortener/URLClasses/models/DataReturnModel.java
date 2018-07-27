package com.shortURL.URL_Shortener.URLClasses.models;

import lombok.Data;

@Data
public class DataReturnModel {
    public Object data;
    public String message;
    public int code;


    public DataReturnModel(Object data, String message, int code) {
        this.data = data;
        this.message = message;
        this.code = code;
    }
}
