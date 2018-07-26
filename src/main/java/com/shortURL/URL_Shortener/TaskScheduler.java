package com.shortURL.URL_Shortener;

import com.shortURL.URL_Shortener.services.URLProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskScheduler {

    @Autowired
    private URLProcessService urlProcessService;

    private static final Logger logger = LoggerFactory.getLogger(TaskScheduler.class);

    //To remove the expired URL every day.
    @Scheduled(cron = "55 23 * * * ?")
    public void removeExpiredURLs() {
        urlProcessService.scanDBforExpiredURLs();
    }
}