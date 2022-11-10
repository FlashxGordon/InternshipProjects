package com.sfa22.ScaleTickets.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sfa22.ScaleTickets.dtos.DailyExpensesDto;
import com.sfa22.ScaleTickets.services.implementations.DailyExpensesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DailyExpensesController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class DailyExpensesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper().
            registerModule(new JavaTimeModule()).setDateFormat(new SimpleDateFormat());

    @MockBean
    private DailyExpensesServiceImpl dailyExpensesService;

    private int ID_1, ID_2;

    static double DAILY_FIXED_COST_1, DAILY_FUEL_COST_1, DAILY_SOFTWARE_COST_1, TOTAL_EXPENSES,
            DAILY_FIXED_COST_2, DAILY_FUEL_COST_2, DAILY_SOFTWARE_COST_2;

    static LocalDate DATE_1, DATE_2, START_DATE, END_DATE;

    static DailyExpensesDto dailyExpensesDto;
    static DailyExpensesDto dailyExpensesDto2;
    List<DailyExpensesDto> dailyExpenses = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        ID_1 = 1;
        ID_2 = 2;
        DAILY_FIXED_COST_1 = 3000.4;
        DAILY_FUEL_COST_1 = 1500.3;
        DAILY_SOFTWARE_COST_1 = 2000;

        TOTAL_EXPENSES = 6500.7;

        DAILY_FIXED_COST_2 = 3000.4;
        DAILY_FUEL_COST_2 = 1500.3;
        DAILY_SOFTWARE_COST_2 = 2000;

        DATE_1 = LocalDate.of(2022, 9, 24);
        DATE_2 = LocalDate.of(2022, 9, 23);

        START_DATE = LocalDate.of(2022, 9, 15);
        END_DATE = LocalDate.of(2022, 9, 23);

        dailyExpensesDto = new DailyExpensesDto(ID_1, DAILY_FIXED_COST_1, DAILY_FUEL_COST_1, DAILY_SOFTWARE_COST_1, DATE_1);
        dailyExpensesDto2 = new DailyExpensesDto(ID_1, DAILY_FIXED_COST_1, DAILY_FUEL_COST_1, DAILY_SOFTWARE_COST_1, DATE_1);

        dailyExpenses.add(new DailyExpensesDto(ID_1, DAILY_FIXED_COST_1, DAILY_FUEL_COST_1, DAILY_SOFTWARE_COST_1, DATE_1));
        dailyExpenses.add(new DailyExpensesDto(ID_2, DAILY_FIXED_COST_2, DAILY_FUEL_COST_2, DAILY_SOFTWARE_COST_2, DATE_2));

    }

    @Test
    void getDailyRevenuesByDate_isStatusOk_andReturnsExpectedValueByDate() throws Exception {
        when(dailyExpensesService.findByExpenseDate(DATE_1)).thenReturn(dailyExpensesDto);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("byDate", DATE_1.toString());

        mockMvc.perform(get("/api/v1/expenses")
                        .params(requestParams)).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(dailyExpensesDto)));
    }

    @Test
    void getByExpenseDateGreaterThan_isStatusOk_isContentCorrect_okay() throws Exception {

        when(dailyExpensesService.getByExpenseDateGreaterThan(DATE_1)).thenReturn(dailyExpenses);

        mockMvc.perform(get("/api/v1/expenses")
                        .param("byDateGreaterThan", DATE_1.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(dailyExpenses)));
    }

    @Test
    void getByExpenseDateBefore_isStatusOk_isContentCorrect_okay() throws Exception {

        when(dailyExpensesService.getByExpenseDateBefore(DATE_1)).thenReturn(dailyExpenses);

        mockMvc.perform(get("/api/v1/expenses")
                        .param("byDateBefore", DATE_1.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(dailyExpenses)));
    }

    @Test
    void getTotalRevenuesInDateRange_isStatusOK_doesReturnCorrectValues() throws Exception {
        when(dailyExpensesService.getByExpenseDateBetween(START_DATE, END_DATE)).thenReturn(dailyExpenses);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("startDate", START_DATE.toString());
        requestParams.add("endDate", END_DATE.toString());

        mockMvc.perform(get("/api/v1/expenses")
                        .params(requestParams)).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(dailyExpenses)));
    }

    @Test
    void calculateDailyExpensesBetween_isStatusOK_doesReturnCorrectValues() throws Exception {
        when(dailyExpensesService.calculateDailyExpensesBetween(START_DATE, END_DATE)).thenReturn(TOTAL_EXPENSES);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("startDate", START_DATE.toString());
        requestParams.add("endDate", END_DATE.toString());

        mockMvc.perform(get("/api/v1/expenses/total")
                        .params(requestParams)).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(TOTAL_EXPENSES)));
    }

    @Test
    void calculateDailyExpensesForSpecificDate_isStatusOK_doesReturnCorrectValues() throws Exception {
        when(dailyExpensesService.calculateDailyExpensesForSpecificDate(DATE_1)).thenReturn(TOTAL_EXPENSES);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("expensesDate", DATE_1.toString());

        mockMvc.perform(get("/api/v1/expenses")
                        .params(requestParams)).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(TOTAL_EXPENSES)));
    }
}
