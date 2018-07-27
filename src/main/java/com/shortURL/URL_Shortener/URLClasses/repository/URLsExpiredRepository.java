package com.shortURL.URL_Shortener.URLClasses.repository;

import com.shortURL.URL_Shortener.URLClasses.models.URLs_DB_Model;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface URLsExpiredRepository extends MongoRepository<URLs_DB_Model, String> {
        }
