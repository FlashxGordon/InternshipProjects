package com.sfa22.ScaleTickets.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sfa22.ScaleTickets.dtos.DiscountDto;
import com.sfa22.ScaleTickets.dtos.DiscountUpdateDto;
import com.sfa22.ScaleTickets.entities.Discount;
import com.sfa22.ScaleTickets.services.implementations.DiscountServiceImpl;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DiscountController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class DiscountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DiscountServiceImpl discountService;

    @InjectMocks
    DiscountController discountController;

    private ObjectMapper objectMapper =
            new ObjectMapper().registerModule(new JavaTimeModule()).setDateFormat(new SimpleDateFormat());

    private String code;

    private int percentage;

    private LocalDate expirationDate;

    Discount discount;

    DiscountDto discountDto;

    List<Discount> allDiscounts;

    List<DiscountDto> allDiscountDtos;


    @BeforeEach
    public void setUp() {

        code = "code10";

        percentage = 15;

        expirationDate = LocalDate.now();

        discount = new Discount(percentage, code, expirationDate);

        discountDto = new DiscountDto(percentage, code, expirationDate);

        allDiscounts = new ArrayList<>();

        allDiscountDtos = new ArrayList<>();

        allDiscounts.add(discount);

        allDiscountDtos.add(discountDto);
    }

    @Test
    void getDiscountById_call_isStatusOkAndCorrecResponse() throws Exception {

        when(discountService.getDiscountById(1)).thenReturn(discountDto);

        mockMvc.perform(get("/api/v1/discounts/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(discountDto)));
    }

    @Test
    void getAllDiscounts_isStatusOk_IsContentCorrect_okay() throws Exception {

        when(discountService.getAllDiscounts()).thenReturn(allDiscountDtos);

        mockMvc.perform(get("/api/v1/discounts"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(allDiscountDtos)));
    }

    @Test
    void getDiscountByExpirationDateAfterAndEqual_isStatusOk_IsContentCorrect_okay() throws Exception {

        when(discountService.getDiscountByExpirationDateAfterAndEqual(expirationDate)).thenReturn(allDiscountDtos);

        mockMvc.perform(get("/api/v1/discounts")
                        .param("expirationDateAfterAndOn", String.valueOf(expirationDate)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(allDiscountDtos)));
    }

    @Test
    void getDiscountByExpirationDateBefore_isStatusOk_IsContentCorrect_okay() throws Exception {

        when(discountService.getDiscountByExpirationDateBefore(expirationDate)).thenReturn(allDiscountDtos);

        mockMvc.perform(get("/api/v1/discounts")
                        .param("expirationDateBefore", String.valueOf(expirationDate)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(allDiscountDtos)));
    }

    @Test
    void getDiscountByCode_isStatusOk_IsContentCorrect_okay() throws Exception {

        when(discountService.getDiscountByCode(code)).thenReturn(discountDto);

        mockMvc.perform(get("/api/v1/discounts")
                        .param("code", code))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(discountDto)));
    }

    @Test
    void getDiscountByCodeAndExpirationDateAfterAndEqual_isStatusOk_IsContentCorrect_okay() throws Exception {

        when(discountService.getDiscountByCodeAndExpirationDateAfterAndEqual(code, expirationDate)).thenReturn(discountDto);

        mockMvc.perform(get("/api/v1/discounts")
                        .param("code", code)
                        .param("expirationDateAfterAndOn", String.valueOf(expirationDate)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(discountDto)));
    }

    @Test
    void addNewDiscount_isStatusOk_okay() throws Exception {

        when(discountService.addNewDiscount(discountDto)).thenReturn(1);

        Map<String, Object> body = new HashMap<>();
            body.put("percentage", percentage);
            body.put("code", code);
        body.put("expirationDate", expirationDate);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/discounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void updateDiscountExpirationDateFoundByCode_isStatusOk_IsContentCorrect_okay() throws Exception {

        discountService.updateDiscountExpirationDateFoundByCode(code, new DiscountUpdateDto(expirationDate));

        Map<String, Object> body = new HashMap<>();
        body.put("expirationDate", expirationDate);
        
        mockMvc.perform(patch("/api/v1/discounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .param("code", code)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}