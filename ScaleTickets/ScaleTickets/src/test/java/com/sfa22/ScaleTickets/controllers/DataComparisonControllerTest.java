package com.sfa22.ScaleTickets.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sfa22.ScaleTickets.dtos.DataComparisonResultDto;

import com.sfa22.ScaleTickets.services.implementations.DataComparisonServiceImpl;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.text.SimpleDateFormat;
import java.time.LocalDate;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(DataComparisonController.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DataComparisonControllerTest.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class DataComparisonControllerTest {

    @MockBean
    DataComparisonServiceImpl dataComparisonService;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper =
            new ObjectMapper().registerModule(new JavaTimeModule()).setDateFormat(new SimpleDateFormat());


    static LocalDate START_DATE_CURRENT, END_DATE_CURRENT, START_DATE_PAST, END_DATE_PAST;

    static DataComparisonResultDto RESULT_DTO;

    static double PERCENT_REVENUES, PERCENT_EXPENSES, PERCENT_PROFITS;

    static double SUM_REVENUES_CURRENT, SUM_EXPENSES_CURRENT, SUM_PROFITS_CURRENT;

    static double SUM_REVENUES_PAST, SUM_EXPENSES_PAST, SUM_PROFITS_PAST, SUM_PROFITS_CURRENT_NEGATIVE;

    @BeforeAll
    public static void setUp() throws Exception {

        START_DATE_CURRENT = LocalDate.of(2022, 9, 1);
        END_DATE_CURRENT = LocalDate.of(2022, 9, 30);

        START_DATE_PAST = LocalDate.of(2022, 8, 1);
        END_DATE_PAST = LocalDate.of(2022, 8, 31);

        SUM_REVENUES_CURRENT = 1000;
        SUM_EXPENSES_CURRENT = 500;
        SUM_PROFITS_CURRENT = 500;

        SUM_PROFITS_CURRENT_NEGATIVE = -100;

        SUM_REVENUES_PAST = 500;
        SUM_EXPENSES_PAST = 250;
        SUM_PROFITS_PAST = 250;

        PERCENT_REVENUES = 100;
        PERCENT_EXPENSES = 100;
        PERCENT_PROFITS = 100;

        RESULT_DTO = new DataComparisonResultDto(START_DATE_CURRENT,
                END_DATE_CURRENT,
                START_DATE_PAST,
                END_DATE_PAST,
                PERCENT_REVENUES,
                PERCENT_EXPENSES,
                PERCENT_PROFITS);

    }

    @Test
    void calculateDailyExpensesForSpecificDate() throws Exception {

        when(dataComparisonService.compareDataInDateRange(START_DATE_CURRENT, END_DATE_CURRENT, START_DATE_PAST, END_DATE_PAST))
                .thenReturn(RESULT_DTO);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("startDateNew", START_DATE_CURRENT.toString());
        requestParams.add("endDateNew", END_DATE_CURRENT.toString());
        requestParams.add("startDateOld", START_DATE_PAST.toString());
        requestParams.add("endDateOld", END_DATE_PAST.toString());

        mockMvc.perform(get("/api/v1/comparison")
                        .params(requestParams))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(RESULT_DTO)));

    }
}