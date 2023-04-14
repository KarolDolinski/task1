package com.kd.assignment.repository;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DataRepository {
    private Map<String, String> db = new HashMap<>();

    protected Map<String, String> getDb() {
        return db;
    }

    protected void setDb(Map<String, String>  db) {
        this.db = db;
    }
}
