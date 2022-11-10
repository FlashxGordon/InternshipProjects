package com.sfa22.ScaleTickets.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sfa22.ScaleTickets.dtos.DailyRevenueDto;
import com.sfa22.ScaleTickets.services.implementations.DailyRevenueServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = DailyRevenueController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class DailyRevenueControllerTest {

    @MockBean
    private DailyRevenueServiceImpl dailyRevenueServiceImpl;
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper().
            registerModule(new JavaTimeModule()).setDateFormat(new SimpleDateFormat());
    static List<DailyRevenueDto> REVENUE_DTO_LIST = new ArrayList<>();

    static Integer REVENUE_ID;
    static DailyRevenueDto REVENUE_DTO_1, REVENUE_DTO_2;
    static LocalDate START_DATE, END_DATE;
    static double INCOME_1, INCOME_2, TOTAL_INCOME;

    @BeforeAll
    public static void setUp() throws Exception {

        START_DATE = LocalDate.of(2022, 9, 20);
        END_DATE = LocalDate.of(2022, 9, 21);

        REVENUE_ID = 2;

        INCOME_1 = 1000;
        INCOME_2 = 1500;
        TOTAL_INCOME = INCOME_1 + INCOME_2;

        REVENUE_DTO_1 = new DailyRevenueDto(INCOME_1, START_DATE);
        REVENUE_DTO_2 = new DailyRevenueDto(INCOME_2, END_DATE);

        REVENUE_DTO_LIST = new ArrayList<>();

        REVENUE_DTO_LIST.add(REVENUE_DTO_1);
        REVENUE_DTO_LIST.add(REVENUE_DTO_2);

    }

    @Test
    void getRevenueId_isStatusOK_returnsExpectedDto() throws Exception {
        when(dailyRevenueServiceImpl.getByRevenueId(REVENUE_ID)).thenReturn(REVENUE_DTO_1);

        mockMvc.perform(get("/api/v1/revenue/2"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(REVENUE_DTO_1)));
    }

    @Test
    void getAllDailyRevenues_isStatusOK_returnsExpectedDto() throws Exception {
        when(dailyRevenueServiceImpl.getAllDailyRevenues()).thenReturn(REVENUE_DTO_LIST);

        mockMvc.perform(get("/api/v1/revenue"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(REVENUE_DTO_LIST)));
    }

    @Test
    void getDailyRevenuesByDate_isStatusOk_andReturnsExpectedValueByDate() throws Exception {
        when(dailyRevenueServiceImpl.getDailyRevenueByDate(START_DATE)).thenReturn(REVENUE_DTO_1);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("byDate", START_DATE.toString());

        mockMvc.perform(get("/api/v1/revenue")
                        .params(requestParams)).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(REVENUE_DTO_1)));
    }

    @Test
    void getTotalRevenuesInDateRange_isStatusOK_doesReturnCorrectValues() throws Exception {
        when(dailyRevenueServiceImpl.getTotalDailyRevenueInDateRange(any(), any())).thenReturn(TOTAL_INCOME);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("startDate", START_DATE.toString());
        requestParams.add("endDate", END_DATE.toString());

        mockMvc.perform(get("/api/v1/revenue/total")
                        .params(requestParams)).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(TOTAL_INCOME)));
    }

    @Test
    void getIncomeByDate_ok() throws Exception {
        when(dailyRevenueServiceImpl.getDailyRevenueIncomeByDate(START_DATE)).thenReturn(INCOME_1);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("byDate", START_DATE.toString());

        mockMvc.perform(get("/api/v1/revenue/income")
                        .params(requestParams)).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(INCOME_1)));
    }

    @Test
    void getListOfRevenuesInDateRange() throws Exception {
        when(dailyRevenueServiceImpl.getListOfDailyRevenuesInDateRange(any(), any())).thenReturn(REVENUE_DTO_LIST);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("startDate", START_DATE.toString());
        requestParams.add("endDate", END_DATE.toString());

        mockMvc.perform(get("/api/v1/revenue")
                        .params(requestParams)).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(REVENUE_DTO_LIST)));
    }
}