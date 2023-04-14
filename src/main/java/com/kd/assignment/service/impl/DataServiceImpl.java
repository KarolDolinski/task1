package com.kd.assignment.service.impl;

import com.kd.assignment.dto.DataRequest;
import com.kd.assignment.dto.Pair;
import com.kd.assignment.repository.TransactionServiceImpl;
import com.kd.assignment.service.DataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class DataServiceImpl implements DataService {
    TransactionServiceImpl transactionService;

    @Override
    public void setData(DataRequest data) {
        Map<String, String> db = getCurrentDb();
        for(Pair pair: data.getData()) {
            db.put(pair.getKey(), pair.getValue());
        }
    }

    @Override
    public void setData(String key, String value) {
        getCurrentDb().put(key, value);
    }

    @Override
    public String getValue(String key) {
        return getCurrentDb().get(key);
    }
    @Override
    public void removeValue(String key) {
        getCurrentDb().remove(key);
    }

    @Override
    public long countData(String value) {
        return getCurrentDb()
                .values().stream()
                .filter(entry -> entry.equals(value)).count();

    }

    private Map<String, String> getCurrentDb() {
        return transactionService.getCurrentDb();
    }
}
