package com.sfa22.ScaleTickets.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sfa22.ScaleTickets.dtos.RouteDto;
import com.sfa22.ScaleTickets.services.implementations.ApiServiceImpl;
import com.sfa22.ScaleTickets.services.implementations.RouteServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RouteController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureMockMvc
public class RouteControllerTest {

    @MockBean
    private RouteServiceImpl routeService;

    @MockBean
    private ApiServiceImpl apiService;
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper().
            registerModule(new JavaTimeModule()).setDateFormat(new SimpleDateFormat());

    static List<RouteDto> routeDtoList = new ArrayList<>();
    static List<RouteDto> routeDtoList2 = new ArrayList<>();

    static List<RouteDto> routeDtoList3 = new ArrayList<>();

    static RouteDto routeDto;

    static RouteDto routeDto2;

    static RouteDto routeDto3;

    static int ID;

    static int newRouteID;
    static String departureCity;

    static String arrivalCity;

    static double tripPrice;

    static double priceGreaterThan;

    static int[] array;

    static double kilometar;

    static Duration travelDuration;

    static double distance;

    @BeforeAll
    public static void setup() {

        ID = 1;

        newRouteID = 1;

        departureCity = "Skopje";

        arrivalCity = "Sofija";

        tripPrice = 150.2;

        priceGreaterThan = 152.2;

        array = new int[]{0};

        long second = 1;

        travelDuration = null;

        distance = 0;

        routeDto = new RouteDto("Skopje", "Sofija", 150.2, null, 0);

        routeDto2 = new RouteDto("Skopje", "Sofija", 155.2, null, 0);

        routeDto3 = new RouteDto("Skopje", "Sofija", 140.2, null, 0);
        routeDtoList.add(routeDto);

        routeDtoList2.add(routeDto2);

        routeDtoList3.add(routeDto3);

    }


    @Test
    void getAllRoutes_isStatusOk_returnsDto() throws Exception {
        when(routeService.getAllRoutes()).thenReturn(routeDtoList);

        mockMvc.perform(get("/api/v1/routes"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(routeDtoList)));
    }

    @Test
    void getRouteByDepartureCity_isStatusOk_isContentCorrect_okay() throws Exception {

        when(routeService.getRouteByDepartureCity(departureCity)).thenReturn(routeDtoList);

        mockMvc.perform(get("/api/v1/routes")
                        .param("departureCity", departureCity))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(routeDtoList)));
    }

    @Test
    void getRouteByArrivalCity_isStatusOk_isContentCorrect_okay() throws Exception {

        when(routeService.getRouteByArrivalCity(arrivalCity)).thenReturn(routeDtoList);

        mockMvc.perform(get("/api/v1/routes")
                        .param("arrivalCity", arrivalCity))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(routeDtoList)));
    }


    @Test
    void getByTripPriceEquals_isStatusOk_returnsDtoList() throws Exception {
        when(routeService.getByTripPriceEquals(tripPrice)).thenReturn(routeDtoList);

        mockMvc.perform(get("/api/v1/routes")
                        .param("priceEquals", String.valueOf(tripPrice)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(routeDtoList)));
    }

    @Test
    void findByTripPriceLessThan_isStatusOk_returnsDtoList() throws Exception {

        when(routeService.findByTripPriceLessThan(tripPrice)).thenReturn(routeDtoList3);

        mockMvc.perform(get("/api/v1/routes")
                        .param("priceLessThan", String.valueOf(tripPrice)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(routeDtoList3)));
    }

    @Test
    void findRouteById_isStatusOk_returnsDto() throws Exception {
        when(routeService.findRouteById(ID)).thenReturn(routeDto);


        mockMvc.perform(get("/api/v1/routes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(routeDto)));
    }

    @Test
    void saveRoute_isStatusOk_okay() throws Exception {

        when(routeService.saveRoute(routeDto)).thenReturn(newRouteID);

        Map<String, Object> body = new HashMap<>();
        body.put("departureCity", departureCity);
        body.put("arrivalCity", arrivalCity);
        body.put("tripPrice", tripPrice);
        body.put("distance", distance);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteRoute_isStatusOk_okay() throws Exception {
        when(routeService.deleteRoute(ID))
                .thenReturn(true);

        mockMvc.perform(delete("/api/v1/routes/1"))
                .andExpect(status().isOk());
    }

}
