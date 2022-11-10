package com.sfa22.ScaleTickets.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sfa22.ScaleTickets.dtos.BusDto;
import com.sfa22.ScaleTickets.entities.Bus;
import com.sfa22.ScaleTickets.services.implementations.BusServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BusController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class BusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusServiceImpl busService;

    private String busPlate;

    private ObjectMapper objectMapper =
            new ObjectMapper().registerModule(new JavaTimeModule()).setDateFormat(new SimpleDateFormat());

    private int busCapacity;

    private double dailyCost;

    private int busID;

    private Bus bus;

    private BusDto busDto;

    private List<BusDto> allBusesDto;

    private List<Bus> allBuses;

    @BeforeEach
    public void setUp() {

        busPlate = "PB0000BP";

        busCapacity = 45;

        dailyCost = 125.50;

        busID = 1;

        bus = new Bus(busID, busPlate, busCapacity, dailyCost);

        busDto = new BusDto(busID, busPlate, busCapacity, dailyCost);

        allBuses = new ArrayList<>();

        allBusesDto = new ArrayList<>();

        allBuses.add(bus);

        allBusesDto.add(busDto);
    }

    @Test
    void getAllBuses_isStatusOk_okay() throws Exception {
        when(busService.getAllBusses()).thenReturn(allBusesDto);

        mockMvc.perform(get("/api/v1/buses"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(allBusesDto)));

    }

    @Test
    void getById_isStatusOk_okay() throws Exception {
        when(busService.getBusById(busID)).thenReturn(Optional.ofNullable(busDto));

        mockMvc.perform(get("/api/v1/buses/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(busDto)));

    }

    @Test
    void getBusByPlate_isStatusOk_isContentCorrect_okay() throws Exception {

        when(busService.getBusByPlate(busPlate)).thenReturn(busDto);

        mockMvc.perform(get("/api/v1/buses")
                        .param("plate", busPlate))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(busDto)));
    }

    @Test
    void getBusesByCapacityLessThan_isStatusOk_isContentCorrect_okay() throws Exception {

        when(busService.getBusesByCapacityLessThan(busCapacity)).thenReturn(allBusesDto);

        mockMvc.perform(get("/api/v1/buses")
                        .param("capacityLessThan", String.valueOf(busCapacity)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(allBusesDto)));
    }

    @Test
    void getBusesByCapacityGreaterThan_isStatusOk_isContentCorrect_okay() throws Exception {

        when(busService.getBusesByCapacityGreaterThan(busCapacity)).thenReturn(allBusesDto);

        mockMvc.perform(get("/api/v1/buses")
                        .param("capacityGreaterThan", String.valueOf(busCapacity)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(allBusesDto)));
    }

    @Test
    void getBusesByCapacityEquals_isStatusOk_isContentCorrect_okay() throws Exception {

        when(busService.getBusesByCapacityEquals(busCapacity)).thenReturn(allBusesDto);

        mockMvc.perform(get("/api/v1/buses")
                        .param("capacity", String.valueOf(busCapacity)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(allBusesDto)));
    }

    @Test
    void getBusesByDailyCostLessThan_isStatusOk_isContentCorrect_okay() throws Exception {

        when(busService.getByDailyCostLessThan(dailyCost)).thenReturn(allBusesDto);

        mockMvc.perform(get("/api/v1/buses")
                        .param("dailyCostLessThan", String.valueOf(dailyCost)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(allBusesDto)));
    }

    @Test
    void getBusesByDailyCostGreaterThan_isStatusOk_isContentCorrect_okay() throws Exception {

        when(busService.getByDailyCostGreaterThan(dailyCost)).thenReturn(allBusesDto);

        mockMvc.perform(get("/api/v1/buses")
                        .param("dailyCostGreaterThan", String.valueOf(dailyCost)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(allBusesDto)));
    }

    @Test
    void getBusesByDailyCostEquals_isStatusOk_isContentCorrect_okay() throws Exception {

        when(busService.getByDailyCostEquals(dailyCost)).thenReturn(allBusesDto);

        mockMvc.perform(get("/api/v1/buses")
                        .param("dailyCost", String.valueOf(dailyCost)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(allBusesDto)));
    }

    @Test
    void getByBusCapacityEqualsAndDailyCostEquals_isStatusOk_isContentCorrect_okay() throws Exception {

        when(busService.getByBusCapacityEqualsAndDailyCostEquals(busCapacity, dailyCost)).thenReturn(allBusesDto);

        mockMvc.perform(get("/api/v1/buses")
                        .param("capacity", String.valueOf(busCapacity))
                        .param("dailyCost", String.valueOf(dailyCost)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(allBusesDto)));
    }

    @Test
    void getBusByPlateAndUpdateFixCost_isStatusOk_okay() throws Exception {

        Map<String, Object> body = new HashMap<>();
        body.put("dailyCost", dailyCost);

        mockMvc.perform(patch("/api/v1/buses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .param("plate", String.valueOf(busPlate))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getBusServiceByPlateNumberAndDelete_isStatusOk_okay() throws Exception {

        mockMvc.perform(delete("/api/v1/buses")
                        .param("plate", String.valueOf(busPlate)))
                .andExpect(status().isOk());
    }

    @Test
    void getDailyCostOfAllBuses_IsStatusOk_isContentCorrect_Okay() throws Exception {

        when(busService.getDailyCostOfAllBuses()).thenReturn(dailyCost);

        mockMvc.perform(get("/api/v1/buses/dailyCost"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(dailyCost)));
    }

    @Test
    void addNewBus_isStatusOk_okay() throws Exception {

        when(busService.addNewBus(busDto)).thenReturn(busID);

        Map<String, Object> body = new HashMap<>();
        body.put("busID", busID);
        body.put("busPlate", busPlate);
        body.put("busCapacity", busCapacity);
        body.put("dailyCost", dailyCost);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/buses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}