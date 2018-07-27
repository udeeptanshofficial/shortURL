package com.shortURL.URL_Shortener.services;

import com.shortURL.URL_Shortener.URLClasses.models.DataReturnModel;
import com.shortURL.URL_Shortener.URLClasses.models.URLsView_ResponseModel;
import com.shortURL.URL_Shortener.URLClasses.models.URLs_DB_Model;
import com.shortURL.URL_Shortener.URLClasses.models.URLs_ResponseModel;
import com.shortURL.URL_Shortener.URLClasses.repository.URLsExpiredRepository;
import com.shortURL.URL_Shortener.URLClasses.repository.URLsInUseRepository;
import com.shortURL.URL_Shortener.helpers.DataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class URLProcessService {
    @Autowired
    private URLsInUseRepository urLsInUseRepository;

    @Autowired
    private URLsExpiredRepository urLsExpiredRepository;

    public DataReturnModel getAllURLEntries() {
        List<URLs_DB_Model> existingURL = urLsInUseRepository.findAll();
        List<URLs_DB_Model> expiredURL = urLsExpiredRepository.findAll();

        URLsView_ResponseModel urLsView_responseModel = new URLsView_ResponseModel();

        List<URLs_ResponseModel> urLs_responseModelList = new ArrayList<>();
        URLs_ResponseModel urLs_responseModel = new URLs_ResponseModel();

        //adding EXISTING urls to the list
        if (existingURL.size() != 0) {
            for (URLs_DB_Model urLs_db_model : existingURL) {
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
        if (expiredURL.size() != 0) {
            for (URLs_DB_Model urLs_db_model : expiredURL) {
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

        if (urLsView_responseModel.getExisting().size() == 0 && urLsView_responseModel.getExpired().size() == 0)
            return new DataReturnModel(urLsView_responseModel, "No expired or existing URLs in the database", 205);

        return new DataReturnModel(urLsView_responseModel, "List of expired and existing URLs", 200);
    }


    public DataReturnModel createShortURL(String longURL, int numberOfDaysValid, String customURL, String securityCode) {
        if (customURL != null) {
            if (!isCustomURLValid(customURL)) {
                return new DataReturnModel("", "Custom URL not available", 210);
            }
        }

        String endDate = DataHelper.getDateAfter(numberOfDaysValid);

        String shortURL = getShortURL();

        URLs_DB_Model urLs_db_model = new URLs_DB_Model();

        if (customURL != null)
            urLs_db_model.setShortURL(customURL);
        else
            urLs_db_model.setShortURL(shortURL);

        if (securityCode != null) {
            urLs_db_model.setSecure(true);
            urLs_db_model.setSecureCode(securityCode);
        } else {
            urLs_db_model.setSecure(false);
            urLs_db_model.setSecureCode(null);
        }

        urLs_db_model.setLongURL(longURL);
        urLs_db_model.setStartDate(DataHelper.getDateNow());
        urLs_db_model.setStartTime(DataHelper.getTimeNow());
        urLs_db_model.setSecureCode(endDate);
        urLs_db_model.setSecureCode(DataHelper.END_TIME);

        urLsInUseRepository.save(urLs_db_model);

        return new DataReturnModel(urLs_db_model, "Successfully created short URL", 200);
    }

    public DataReturnModel getLongURL(String shortURL){
        URLs_DB_Model urLs_db_model = urLsInUseRepository.findByShortURL(shortURL);

        if (urLs_db_model!=null){
            return new DataReturnModel(urLs_db_model, "Long URL found in the data base", 200);
        }

        return new DataReturnModel(urLs_db_model, "No Long URL found in the data base", 204);
    }

    public void scanDBforExpiredURLs() {
        List<URLs_DB_Model> existingURL = urLsInUseRepository.findAll();

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(DataHelper.DATE_PATTERN);
        String today = dateFormat.format(date);
        today = today.split(" ")[0];

        if (existingURL.size() != 0) {
            for (URLs_DB_Model urLs_db : existingURL) {
                if (today.equals(urLs_db.getEndDate()))
                    removeShortURL(urLs_db.getShortURL());
            }
        }
    }

    private void removeShortURL(String shortURL) {
        //Fetching URL details from In Use Repository
        URLs_DB_Model urLs_db_model = urLsInUseRepository.findByShortURL(shortURL);

        //Saving the expired URL in Expired Repository
        urLsExpiredRepository.save(urLs_db_model);

        //Deleting from In Use Repository
        urLsInUseRepository.delete(urLs_db_model);
    }

    /*
     *This is the Main piece
     */
    private String getShortURL() {
        return "";
    }

    private boolean isCustomURLValid(String customURL) {
        URLs_DB_Model urLsDbModel = urLsInUseRepository.findByShortURL(customURL);
        return urLsDbModel == null;
    }

}

