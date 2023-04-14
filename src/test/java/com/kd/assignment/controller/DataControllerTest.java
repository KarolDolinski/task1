package com.kd.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kd.assignment.dto.DataRequest;
import com.kd.assignment.dto.Pair;
import com.kd.assignment.service.DataService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DataController.class)
public class DataControllerTest {

    public static final String DATA_KEY_PATH = "/data/{key}";
    public static final String DATA_KEY_COUNT_PATH = "/data/{key}/count";
    public static final String DATA_PATH = "/data";
    @MockBean
    DataService dataService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createData_callsService_returnsCreated() throws Exception {
        DataRequest request = DataRequest.builder()
                .data(List.of(new Pair(TEST_KEY, TEST_VALUE)))
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post(DATA_PATH)
                        .header("Content-Type", "application/json")
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(dataService, times(1)).setData(request);
    }

    @Test
    public void createData_nullData_throwsException() throws Exception {
        DataRequest request = DataRequest.builder()
                .data(null)
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post(DATA_PATH)
                        .header("Content-Type", "application/json")
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(dataService, times(0)).setData(request);
    }

    @Test
    public void createData_emptyData_throwsException() throws Exception {
        DataRequest request = DataRequest.builder()
                .data(List.of(new Pair("", "")))
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post(DATA_PATH)
                        .header("Content-Type", "application/json")
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(dataService, times(0)).setData(request);
    }

    @Test
    public void createData_wrongKey_returnsBadRequest() throws Exception {
        DataRequest request = DataRequest.builder()
                .data(List.of(new Pair("*", "das")))
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post(DATA_PATH)
                        .header("Content-Type", "application/json")
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(dataService, times(0)).setData(request);
    }



    @Test
    public void createData_longKey_returnsBadRequest() throws Exception {
        DataRequest request = DataRequest.builder()
                .data(List.of(new Pair("1234567889012", "das")))
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post(DATA_PATH)
                        .header("Content-Type", "application/json")
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(dataService, times(0)).setData(request);
    }
    @Test
    public void createData_wrongValue_returnsBadRequest() throws Exception {
        DataRequest request = DataRequest.builder()
                .data(List.of(new Pair(TEST_KEY,  "-1")))
                .build();

        mvc.perform(MockMvcRequestBuilders
                        .post(DATA_PATH)
                        .header("Content-Type", "application/json")
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(dataService, times(0)).setData(request);
    }

    @Test
    void getValue_callsService_returnsOk() throws Exception {
        when(dataService.getValue(TEST_KEY))
                .thenReturn(TEST_VALUE);
        mvc.perform(MockMvcRequestBuilders
                        .get(DATA_KEY_PATH, TEST_KEY))
                .andExpect(status().isOk())
                .andExpect(content().string(TEST_VALUE));
        verify(dataService, times(1)).getValue(TEST_KEY);
    }

    @Test
    void deleteValue_callsService_returnsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete(DATA_KEY_PATH, TEST_KEY))
                .andExpect(status().isOk());
        verify(dataService, times(1)).removeValue(TEST_KEY);
    }

    @Test
    void getCount_callsService_returnsCount() throws Exception {
        when(dataService.countData(TEST_KEY))
                .thenReturn(2L);
        mvc.perform(MockMvcRequestBuilders
                        .get(DATA_KEY_COUNT_PATH, TEST_KEY))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
        verify(dataService, times(1)).countData(TEST_KEY);
    }
}
