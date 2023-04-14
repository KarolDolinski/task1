package com.kd.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kd.assignment.dto.DataRequest;
import com.kd.assignment.dto.Pair;
import com.kd.assignment.exception.TransactionException;
import com.kd.assignment.repository.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.kd.assignment.TestUtil.TEST_KEY;
import static com.kd.assignment.TestUtil.TEST_VALUE;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
    public static final String TRANSACTION_PATH = "/data/transaction";

    public static final String TRANSACTION_COMMIT_PATH = "/data/transaction/commit";

    public static final String TRANSACTION_ROLLBACK_PATH = "/data/transaction/rollback";

    @MockBean
    TransactionServiceImpl transactionService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void startTransaction_callsService_returnsOk() throws Exception {
        DataRequest request = DataRequest.builder()
                .data(List.of(new Pair(TEST_KEY, TEST_VALUE)))
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post(TRANSACTION_PATH)
                        .header("Content-Type", "application/json")
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(transactionService).begin();
    }


    @Test
    void commit_callsService_returnsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post(TRANSACTION_COMMIT_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(transactionService, times(1)).commit();
    }

    @Test
    void rollback_callsService_returnsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post(TRANSACTION_ROLLBACK_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(transactionService, times(1)).rollback();
    }

    @Test
    void rollback_throwsException_returnsBadRequest() throws Exception {
        doThrow(new TransactionException(""))
                .when(transactionService).rollback();
        mvc.perform(MockMvcRequestBuilders
                        .post(TRANSACTION_ROLLBACK_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(transactionService, times(1)).rollback();
    }
}