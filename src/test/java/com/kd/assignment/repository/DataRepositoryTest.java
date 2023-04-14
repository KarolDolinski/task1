package com.kd.assignment.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.kd.assignment.TestUtil.TEST_KEY;
import static com.kd.assignment.TestUtil.TEST_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataRepositoryTest {

    private DataRepository dataRepository;

    @BeforeEach
    public void setUp() {
        dataRepository = new DataRepository();
    }
    @Test
    void set_setsDB_retrievesProperDb() {
        Map<String, String> db = new HashMap<>();
        db.put(TEST_KEY, TEST_VALUE);
        dataRepository.setDb(db);
        assertEquals(TEST_VALUE ,dataRepository.getDb().get(TEST_KEY));
    }
}