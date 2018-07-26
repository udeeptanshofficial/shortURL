package com.shortURL.URL_Shortener.services;

import com.shortURL.URL_Shortener.dataHandlingClasses.models.DataReturnModel;
import com.shortURL.URL_Shortener.dataHandlingClasses.models.URLsView_ResponseModel;
import com.shortURL.URL_Shortener.dataHandlingClasses.models.URLs_DB_Model;
import com.shortURL.URL_Shortener.dataHandlingClasses.models.URLs_ResponseModel;
import com.shortURL.URL_Shortener.dataHandlingClasses.repository.URLsExpiredRepository;
import com.shortURL.URL_Shortener.dataHandlingClasses.repository.URLsInUseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class URLProcessService {
    @Autowired
    private URLsInUseRepository urLsInUseRepository;

    @Autowired
    private URLsExpiredRepository urLsExpiredRepository;

    public DataReturnModel getAllURLEntries(){
        List<URLs_DB_Model> existingURL = urLsInUseRepository.findAll();
        List<URLs_DB_Model> expiredURL = urLsExpiredRepository.findAll();

        URLsView_ResponseModel urLsView_responseModel = new URLsView_ResponseModel();

        List<URLs_ResponseModel> urLs_responseModelList = new ArrayList<>();
        URLs_ResponseModel urLs_responseModel = new URLs_ResponseModel();

        //adding EXISTING urls to the list
        if (existingURL.size()!=0){
            for (URLs_DB_Model urLs_db_model : existingURL){
                urLs_responseModel.setShortURL(urLs_db_model.getShortURL());
                urLs_responseModel.setLongURL(urLs_db_model.getLongURL());
                urLs_responseModel.setSecure(urLs_db_model.isSecure());
                urLs_responseModel.setSecureCode(urLs_db_model.getSecureCode());
                urLs_responseModel.setStartDate(urLs_db_model.getStartDate());
                urLs_responseModel.setStartTime(urLs_db_model.getStartTime());
                urLs_responseModel.setEndDate(urLs_db_model.getEndDate());
                urLs_responseModel.setEndTime(urLs_db_model.getEndTime());
                urLs_responseModel.setExisting(true);
                urLs_responseModel.setNumberOfCharInLongURL(urLs_db_model.getLongURL().length());
                urLs_responseModel.setNumberOfCharInShortURL(urLs_db_model.getLongURL().length());

                urLs_responseModelList.add(urLs_responseModel);
            }
        }

        urLsView_responseModel.setExisting(urLs_responseModelList);

        urLs_responseModelList = new ArrayList<>();

        //adding EXPIRED urls to the list
        if (expiredURL.size()!=0){
            for (URLs_DB_Model urLs_db_model : expiredURL){
                urLs_responseModel.setShortURL(urLs_db_model.getShortURL());
                urLs_responseModel.setLongURL(urLs_db_model.getLongURL());
                urLs_responseModel.setSecure(urLs_db_model.isSecure());
                urLs_responseModel.setSecureCode(urLs_db_model.getSecureCode());
                urLs_responseModel.setStartDate(urLs_db_model.getStartDate());
                urLs_responseModel.setStartTime(urLs_db_model.getStartTime());
                urLs_responseModel.setEndDate(urLs_db_model.getEndDate());
                urLs_responseModel.setEndTime(urLs_db_model.getEndTime());
                urLs_responseModel.setExisting(false);
                urLs_responseModel.setNumberOfCharInLongURL(urLs_db_model.getLongURL().length());
                urLs_responseModel.setNumberOfCharInShortURL(urLs_db_model.getLongURL().length());

                urLs_responseModelList.add(urLs_responseModel);
            }
        }

        urLsView_responseModel.setExpired(urLs_responseModelList);

        if (urLsView_responseModel.getExisting().size()==0 && urLsView_responseModel.getExpired().size()==0)
            return new DataReturnModel(urLsView_responseModel,"No expired or existing URLs in the database", 205);

        return new DataReturnModel(urLsView_responseModel,"List of expired and existing URLs", 200);
    }


//    public DataReturnModel createShortURL(String longURL, ) {
//
//    }
//
//    public DataReturnModel createShortURL(String longURL, String securityCode) {
//
//    }

    public void scanDBforExpiredURLs(){
        List<URLs_DB_Model> existingURL = urLsInUseRepository.findAll();

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String today = dateFormat.format(date);

        if (existingURL.size()!=0){
            for (URLs_DB_Model urLs_db : existingURL){
                if (today.equals(urLs_db.getEndDate()))
                    removeShortURL(urLs_db.getShortURL());
            }
        }
    }

    private void removeShortURL(String shortURL){
        //Fetching URL details from In Use Repository
        URLs_DB_Model urLs_db_model = urLsInUseRepository.findByShortURL(shortURL);

        //Saving the expired URL in Expired Repository
        urLsExpiredRepository.save(urLs_db_model);

        //Deleting from In Use Repository
        urLsInUseRepository.delete(urLs_db_model);
    }

}

