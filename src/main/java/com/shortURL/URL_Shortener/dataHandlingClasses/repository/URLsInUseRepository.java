package com.shortURL.URL_Shortener.dataHandlingClasses.repository;

import com.shortURL.URL_Shortener.dataHandlingClasses.models.URLs_DB_Model;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface URLsInUseRepository extends MongoRepository<URLs_DB_Model, String> {
    public URLs_DB_Model findByShortURL(String shortURL);
}
