package com.shortURL.URL_Shortener.controllers;

import com.shortURL.URL_Shortener.services.URLProcessService;
import com.shortURL.URL_Shortener.dataHandlingClasses.models.DataReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class URLProcessController {

    @Autowired
    private URLProcessService URLProcessService;

    @RequestMapping("/")
    public String test() {
        return "The server is up!";
    }

//    @PostMapping(value = "/createURL", produces = MediaType.APPLICATION_JSON_VALUE)
//    public DataReturnModel createShortURL(@RequestBody ShortURL_RequestModel shortURL_request){
//        if (shortURL_request.customURL!=null){
//
//        }
//
//
//        DataReturnModel dataReturnModel = new DataReturnModel();
//        dataReturnModel.setCode(200);
//        dataReturnModel.setMessage("");
//
//    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataReturnModel getAllURLEntries(){
        return URLProcessService.getAllURLEntries();
    }


}