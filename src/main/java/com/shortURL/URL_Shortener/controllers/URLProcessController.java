package com.shortURL.URL_Shortener.controllers;

import com.shortURL.URL_Shortener.URLClasses.models.AdminLoginDetailsModel;
import com.shortURL.URL_Shortener.URLClasses.models.DataReturnModel;
import com.shortURL.URL_Shortener.URLClasses.models.SecureURL_RequestModel;
import com.shortURL.URL_Shortener.URLClasses.models.ShortURL_RequestModel;
import com.shortURL.URL_Shortener.services.URLProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
public class URLProcessController {

    @Autowired
    private URLProcessService URLProcessService;

    @PostMapping("/test")
    public String test() {
        return "The server is up!";
    }

    @PostMapping(value = "/createURL", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataReturnModel createShortURL(@RequestBody ShortURL_RequestModel shortURL_request) throws NoSuchAlgorithmException {
        return URLProcessService.createShortURL(shortURL_request.getLongURL(), shortURL_request.getNumberOfDaysValid(), shortURL_request.getCustomURL(), shortURL_request.getSecureCode());
    }

    @GetMapping(value = "/{shortURL}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataReturnModel getLongURL(@PathVariable String shortURL){
        return URLProcessService.getLongURL(shortURL);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataReturnModel getAllURLEntries(){
        return URLProcessService.getAllURLEntries();
    }

    @PostMapping(value = "/getSecureURL", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataReturnModel getSecureLongURL(@RequestBody SecureURL_RequestModel secureURL_requestModel) throws NoSuchAlgorithmException {
        return URLProcessService.getSecureLongURL(secureURL_requestModel);
    }

    @PostMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataReturnModel loginAdmin(@RequestBody AdminLoginDetailsModel adminLoginDetailsModel){
        return URLProcessService.loginAdmin(adminLoginDetailsModel);
    }
}