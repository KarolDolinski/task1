package com.kd.assignment.service.impl;

import com.kd.assignment.dto.DataRequest;
import com.kd.assignment.dto.Pair;
import com.kd.assignment.repository.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kd.assignment.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class DataServiceImplTest {
    public static final int EXPECTED_COUNT = 2;
    @Mock
    TransactionServiceImpl transactionService;

    @InjectMocks
    private DataServiceImpl dataService;
    @Test
    void setData_setsDb_setsValue() {
        Map<String, String> db = new HashMap<>();
        when(transactionService.getCurrentDb())
                .thenReturn(db);

        DataRequest request = DataRequest.builder()
                .data(List.of(new Pair(TEST_KEY, TEST_VALUE)))
                .build();
        dataService.setData(request);
        assertEquals(TEST_VALUE, db.get(TEST_KEY));
    }

    @Test
    void getValue_callsDb_getsValue() {
        Map<String, String> db = new HashMap<>();
        when(transactionService.getCurrentDb())
                .thenReturn(db);

        DataRequest request = DataRequest.builder()
                .data(List.of(new Pair(TEST_KEY, TEST_VALUE)))
                .build();
        dataService.setData(request);

        assertEquals(TEST_VALUE, dataService.getValue(TEST_KEY));
    }

    @Test
    void removeValue_callsDb_removeValue() {
        Map<String, String> db = new HashMap<>();
        when(transactionService.getCurrentDb())
                .thenReturn(db);

        DataRequest request = DataRequest.builder()
                .data(List.of(new Pair(TEST_KEY, TEST_VALUE)))
                .build();
        dataService.setData(request);

        dataService.removeValue(TEST_KEY);
        assertEquals(null, dataService.getValue(TEST_KEY));
    }

    @Test
    void countData_callsDb_countsValues() {
        Map<String, String> db = new HashMap<>();
        when(transactionService.getCurrentDb())
                .thenReturn(db);

        DataRequest request = DataRequest.builder()
                .data(List.of(new Pair(TEST_KEY, TEST_VALUE),
                        new Pair(TEST_KEY1, TEST_VALUE)))
                .build();
        dataService.setData(request);
        assertEquals(EXPECTED_COUNT, dataService.countData(TEST_VALUE));
    }

    @Test
    void countData_noData_countsValues() {
        Map<String, String> db = new HashMap<>();
        when(transactionService.getCurrentDb())
                .thenReturn(db);
        assertEquals(0, dataService.countData(TEST_VALUE));
    }
}