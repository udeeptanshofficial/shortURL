package com.shortURL.URL_Shortener.services;

import com.shortURL.URL_Shortener.URLClasses.ShortURLClass;
import com.shortURL.URL_Shortener.URLClasses.models.*;
import com.shortURL.URL_Shortener.URLClasses.repository.URLsExpiredRepository;
import com.shortURL.URL_Shortener.URLClasses.repository.URLsInUseRepository;
import com.shortURL.URL_Shortener.helpers.DataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
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

    public DataReturnModel getAllURLEntries() {
        List<URLs_DB_Model> existingURL = urLsInUseRepository.findAll();
        List<URLs_DB_Model> expiredURL = urLsExpiredRepository.findAll();

        URLsView_ResponseModel urLsView_responseModel = new URLsView_ResponseModel();

        List<URLs_ResponseModel> urLs_responseModelList = new ArrayList<>();
        URLs_ResponseModel urLs_responseModel = new URLs_ResponseModel();

        //adding EXISTING urls to the list
        if (existingURL.size() != 0) {
            for (URLs_DB_Model urLs_db_model : existingURL) {
                urLs_responseModel.setShortURL(ShortURLClass.encode(new BigInteger(urLs_db_model.getShortURL())));
                urLs_responseModel.setLongURL(urLs_db_model.getLongURL());
                urLs_responseModel.setSecure(urLs_db_model.isSecure());
                urLs_responseModel.setSecureCode(urLs_db_model.getSecurityKey());
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
                urLs_responseModel.setShortURL(ShortURLClass.encode(new BigInteger(urLs_db_model.getShortURL())));
                urLs_responseModel.setLongURL(urLs_db_model.getLongURL());
                urLs_responseModel.setSecure(urLs_db_model.isSecure());
                urLs_responseModel.setSecureCode(urLs_db_model.getSecurityKey());
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
        if (!DataHelper.isValidURL(longURL)){
            return new DataReturnModel("", "The URL entered is not valid!", 218);
        }

        if (customURL != null) {
            if (!isCustomURLValid(customURL)) {
                return new DataReturnModel("", "Custom URL not available", 210);
            }
            else if (!DataHelper.isValidShortURL(customURL)){
                return new DataReturnModel("", "Not a valid custom URL. It should not contain any special character or spaces.", 220);
            }
        }

        String endDate = DataHelper.getDateAfter(numberOfDaysValid);

        String shortURL_Numbered = String.valueOf(DataHelper.getRandomBigInteger());

        URLs_DB_Model urLs_db_model = new URLs_DB_Model();

        if (customURL != null)
            urLs_db_model.setShortURL(String.valueOf(ShortURLClass.decode(customURL)));
        else
            urLs_db_model.setShortURL(shortURL_Numbered);

        if (securityCode != null) {
            urLs_db_model.setSecure(true);
            urLs_db_model.setSecurityKey(securityCode);
        } else {
            urLs_db_model.setSecure(false);
            urLs_db_model.setSecurityKey(null);
        }

        urLs_db_model.setLongURL(longURL);
        urLs_db_model.setStartDate(DataHelper.getDateNow());
        urLs_db_model.setStartTime(DataHelper.getTimeNow());
        urLs_db_model.setSecurityKey(endDate);
        urLs_db_model.setSecurityKey(DataHelper.END_TIME);

        urLsInUseRepository.save(urLs_db_model);

        urLs_db_model.setShortURL(ShortURLClass.encode(new BigInteger(urLs_db_model.getShortURL())));

        return new DataReturnModel(urLs_db_model, "Successfully created short URL", 200);
    }

    public DataReturnModel getLongURL(String shortURL){
        if (shortURL==null){
            return new DataReturnModel(new URLs_DB_Model(), "No Long URL found in the data base", 204);
        }

        URLs_DB_Model urLs_db_model = urLsInUseRepository.findByShortURL(String.valueOf(ShortURLClass.decode(shortURL)));

        if (urLs_db_model==null){
            return new DataReturnModel(new URLs_DB_Model(), "No Long URL found in the data base", 204);
        }

        if (urLs_db_model.isSecure)
            return new DataReturnModel(new URLs_DB_Model(), "This is a secure URL. Security Key required", 207);

        urLs_db_model.setShortURL(ShortURLClass.encode(new BigInteger(urLs_db_model.getShortURL())));
        return new DataReturnModel(urLs_db_model, "Long URL found in the data base", 200);
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

    public DataReturnModel getSecureLongURL(SecureURL_RequestModel secureURL_requestModel) throws NoSuchAlgorithmException {
        if (secureURL_requestModel.getShortURL()==null){
            return new DataReturnModel(new URLs_DB_Model(), "No Long URL found in the data base", 204);
        }

        URLs_DB_Model urLs_db_model = urLsInUseRepository.findByShortURL(String.valueOf(ShortURLClass.decode(secureURL_requestModel.getShortURL())));

        if (urLs_db_model==null){
            return new DataReturnModel(new URLs_DB_Model(), "URL not found for this short URL", 204);
        }

        if (!urLs_db_model.isSecure)
            return new DataReturnModel(new URLs_DB_Model(), "This is not a secure URL. Security Key is not required", 206);

        if (DataHelper.getEncryptedKey(secureURL_requestModel.getSecurityKey()).equals(DataHelper.getEncryptedKey(urLs_db_model.getSecurityKey()))){
            urLs_db_model.setShortURL(ShortURLClass.encode(new BigInteger(urLs_db_model.getShortURL())));
            return new DataReturnModel(urLs_db_model, "Long URL found in the data base", 200);
        }

        return new DataReturnModel(new URLs_DB_Model(), "Invalid Security Key", 200);
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

