package com.sfa22.ScaleTickets.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sfa22.ScaleTickets.dtos.DailyGrossProfitDto;
import com.sfa22.ScaleTickets.services.interfaces.DailyGrossProfitService;
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
@WebMvcTest(controllers = DailyGrossProfitController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class DailyGrossProfitControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper().
            registerModule(new JavaTimeModule()).setDateFormat(new SimpleDateFormat());

    @MockBean
    private DailyGrossProfitService dailyGrossProfitService;

    private int ID_1, ID_2;
    private Double PROFIT_1, PROFIT_2, PROFIT_SUM;
    private LocalDate DATE_1, DATE_2, START_DATE, END_DATE;

    List<DailyGrossProfitDto> dailyGrossProfits = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        ID_1 = 1;
        ID_2 = 2;

        PROFIT_1 = 132.4;
        PROFIT_2 = -31.3;
        PROFIT_SUM = PROFIT_1 + PROFIT_2;

        DATE_1 = LocalDate.of(2022, 9, 20);
        DATE_2 = LocalDate.of(2022, 9, 19);

        START_DATE = LocalDate.of(2022, 9, 18);
        END_DATE = LocalDate.of(2022, 9, 23);

        dailyGrossProfits.add(new DailyGrossProfitDto(ID_1, PROFIT_1, DATE_1));
        dailyGrossProfits.add(new DailyGrossProfitDto(ID_2, PROFIT_2, DATE_2));
    }

    @Test
    void getAllDailyGrossProfits_validCall_statusIsOk() throws Exception {
        mockMvc.perform(get("/api/v1/grossProfit"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllDailyGrossProfits_validCall_correctResponseBody() throws Exception {
        when(dailyGrossProfitService.getAllDailyGrossProfit()).thenReturn(dailyGrossProfits);

        mockMvc.perform(get("/api/v1/grossProfit"))
                .andExpect(content().string(objectMapper.writeValueAsString(dailyGrossProfits)));
    }

    @Test
    void getDailyGrossProfitsByDate_validCall_statusIsOk() throws Exception {
        when(dailyGrossProfitService.getByProfitDate(DATE_1)).thenReturn(dailyGrossProfits.get(0));

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("byDate", DATE_1.toString());

        mockMvc.perform(get("/api/v1/grossProfit").params(requestParams))
                .andExpect(status().isOk());
    }

    @Test
    void getDailyGrossProfitsByDate_validCall_correctResponseBody() throws Exception {
        when(dailyGrossProfitService.getByProfitDate(DATE_1)).thenReturn(dailyGrossProfits.get(0));

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("byDate", DATE_1.toString());

        mockMvc.perform(get("/api/v1/grossProfit").params(requestParams))
                .andExpect(content().string(objectMapper.writeValueAsString(dailyGrossProfits.get(0))));
    }

    @Test
    void getTotalGrossProfitInDateRange_validCall_statusIsOk() throws Exception {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("startDate", START_DATE.toString());
        requestParams.add("endDate", END_DATE.toString());

        mockMvc.perform(get("/api/v1/grossProfit/total").params(requestParams))
                .andExpect(status().isOk());
    }

    @Test
    void getTotalGrossProfitInDateRange_validCall_correctResponseBody() throws Exception {
        when(dailyGrossProfitService.calculateDailyGrossProfitInDateRange(START_DATE, END_DATE)).thenReturn(PROFIT_SUM);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("startDate", START_DATE.toString());
        requestParams.add("endDate", END_DATE.toString());

        mockMvc.perform(get("/api/v1/grossProfit/total").params(requestParams))
                .andExpect(content().string(objectMapper.writeValueAsString(PROFIT_SUM)));
    }

    @Test
    void getListOfGrossProfitsInDateRange_validCall_statusIsOk() throws Exception {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("startDate", START_DATE.toString());
        requestParams.add("endDate", END_DATE.toString());

        mockMvc.perform(get("/api/v1/grossProfit").params(requestParams))
                .andExpect(status().isOk());
    }

    @Test
    void getListOfGrossProfitsInDateRange_validCall_correctResponseBody() throws Exception {
        when(dailyGrossProfitService.getByProfitDateBetween(START_DATE, END_DATE)).thenReturn(dailyGrossProfits);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("startDate", START_DATE.toString());
        requestParams.add("endDate", END_DATE.toString());

        mockMvc.perform(get("/api/v1/grossProfit").params(requestParams))
                .andExpect(content().string(objectMapper.writeValueAsString(dailyGrossProfits)));
    }
}