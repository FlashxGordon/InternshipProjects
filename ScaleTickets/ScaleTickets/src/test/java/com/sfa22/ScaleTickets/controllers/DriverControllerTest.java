package com.sfa22.ScaleTickets.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sfa22.ScaleTickets.dtos.DriverDto;
import com.sfa22.ScaleTickets.services.implementations.DriverServiceImpl;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DriverController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureMockMvc
public class DriverControllerTest {

    @MockBean
    private DriverServiceImpl driverService;
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper().
            registerModule(new JavaTimeModule()).setDateFormat(new SimpleDateFormat());

    static List<DriverDto> driverDtoList = new ArrayList<>();

    static List<DriverDto> driverDtoList2 = new ArrayList<>();

    static List<DriverDto> driverDtoList3 = new ArrayList<>();

    static DriverDto driverDto;

    static DriverDto driverDto2;

    static DriverDto driverDto3;

    static String firstName;

    static String lastName;

    static String phoneNumber;

    static Double dailyWage;

    static Double dailyWageLessThan;

    static Double dailyWageEquals;

    static Double dailyWageGreaterThan;

    static int newDriverID;

    @BeforeAll
    public static void setup() {
        firstName = "Kristina";

        lastName = "Lajmanovska";

        phoneNumber = "077/254-265";

        dailyWage = 232.2;

        dailyWageLessThan = 232.1;

        dailyWageEquals = 232.2;

        dailyWageGreaterThan = 232.3;

        newDriverID = 1;

        driverDto = new DriverDto("Kristina", "Lajmanovska", "077/254/265", 232.2);

        driverDto2 = new DriverDto("Marija", "Lajmanovska", "077/159/265", 232.0);

        driverDto3 = new DriverDto("Ana", "Lajmanovska", "077/159/265", 232.3);

        driverDtoList.add(driverDto);

        driverDtoList2.add(driverDto2);

        driverDtoList3.add(driverDto3);

    }

    @Test
    void getAllDrivers_isStatusOk_returnsDto() throws Exception {
        when(driverService.getAllDrivers()).thenReturn(driverDtoList);

        mockMvc.perform(get("/api/v1/drivers/"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(driverDtoList)));
    }

    @Test
    void getDriverById_isStatusOk_okay() throws Exception {
        when(driverService.getDriverById(1)).thenReturn(Optional.ofNullable(driverDto));

        mockMvc.perform(get("/api/v1/drivers/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(driverDto)));

    }

    @Test
    void getDriversByFirstName_isStatusOk_returnsDtoList() throws Exception {
        when(driverService.getDriverByFirstName(any())).thenReturn(driverDtoList);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("firstName", firstName.toString());

        mockMvc.perform(get("/api/v1/drivers/")
                        .params(requestParams)).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(driverDtoList)));
    }

    @Test
    void getDriversByLastName_isStatusOk_returnsDtoList() throws Exception {
        when(driverService.getDriverByLastName(any())).thenReturn(driverDtoList);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("lastName", lastName.toString());

        mockMvc.perform(get("/api/v1/drivers/")
                        .params(requestParams)).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(driverDtoList)));
    }

    @Test
    void getDriverByPhoneNumber_isStatusOk_returnsDto() throws Exception {
        when(driverService.getDriverByPhoneNumber(any())).thenReturn(driverDto);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("phoneNumber", phoneNumber.toString());

        mockMvc.perform(get("/api/v1/drivers/")
                        .params(requestParams)).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(driverDto)));
    }

    @Test
    void getDriverByDailyWageLessThan_isStatusOk_returnsDtoList() throws Exception {
        when(driverService.getDriverByDailyWageLessThan(dailyWage)).thenReturn(driverDtoList2);

        mockMvc.perform(get("/api/v1/drivers/")
                        .param("dailyWageLessThan", String.valueOf(dailyWage)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(driverDtoList2)));
    }

    @Test
    void getDriverByDailyWageGreaterThan_isStatusOk_returnsDtoList() throws Exception {
        when(driverService.getDriverByDailyWageGreaterThan(dailyWage)).thenReturn(driverDtoList3);

        mockMvc.perform(get("/api/v1/drivers/")
                        .param("dailyWageGreaterThan", String.valueOf(dailyWage)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(driverDtoList3)));
    }

    @Test
    void getDriverByDailyWageEquals_isStatusOk_returnsDtoList() throws Exception {
        when(driverService.getDriverByDailyWageEquals(dailyWage)).thenReturn(driverDtoList);

        mockMvc.perform(get("/api/v1/drivers/")
                        .param("dailyWageEquals", String.valueOf(dailyWage)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(driverDtoList)));
    }

    @Test
    void addNewDriver_isStatusOk_okay() throws Exception {

        when(driverService.addNewDriver(driverDto)).thenReturn(newDriverID);

        Map<String, Object> body = new HashMap<>();
        //body.put("busID", busID);
        body.put("firstName", firstName);
        body.put("lastName", lastName);
        body.put("phoneNumber", phoneNumber);
        body.put("dailyWage", dailyWage);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteDriverByPhoneNumber_isStatusOk_okay() throws Exception {

        mockMvc.perform(delete("/api/v1/drivers")
                        .param("phoneNumber", phoneNumber.toString()))
                .andExpect(status().isOk());
    }

}

