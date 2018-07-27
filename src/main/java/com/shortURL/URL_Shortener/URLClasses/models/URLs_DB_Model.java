package com.shortURL.URL_Shortener.URLClasses.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class URLs_DB_Model {
    @Id
    public String id;

    public String shortURL;
    public String longURL;
    public boolean isSecure;
    public String securityKey;
    public String startDate;
    public String startTime;
    public String endDate;
    public String endTime;
}
