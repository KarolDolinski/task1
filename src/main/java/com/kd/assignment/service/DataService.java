package com.kd.assignment.service;


import com.kd.assignment.dto.DataRequest;

public interface DataService {
    void setData(DataRequest data);

    void setData(String key, String value);

    String getValue(String key);

    void removeValue(String key);

    long countData(String key);

}