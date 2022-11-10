package com.sfa22.ScaleTickets.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfa22.ScaleTickets.dtos.DetailedTripDto;
import com.sfa22.ScaleTickets.dtos.TripDto;
import com.sfa22.ScaleTickets.dtos.TripRequestDto;
import com.sfa22.ScaleTickets.entities.Bus;
import com.sfa22.ScaleTickets.entities.Driver;
import com.sfa22.ScaleTickets.entities.Route;
import com.sfa22.ScaleTickets.services.implementations.TripServiceImpl;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TripController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class TripControllerTest {
    @MockBean
    TripServiceImpl tripService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Bus> buses = new ArrayList<>();
    private List<Driver> drivers = new ArrayList<>();
    private List<Route> routes = new ArrayList<>();
    private List<TripDto.RouteDto> routeDtos = new ArrayList<>();
    private List<TripDto> tripDtos = new ArrayList<>();
    private List<DetailedTripDto> detailedTripDtos = new ArrayList<>();
    private List<TripDto.BusDto> busDtos = new ArrayList<>();


    @BeforeEach
    void setUp() {
        buses = new ArrayList<>();
        busDtos = new ArrayList<>();
        drivers = new ArrayList<>();
        routes = new ArrayList<>();
        routeDtos = new ArrayList<>();
        tripDtos = new ArrayList<>();
        detailedTripDtos = new ArrayList<>();

        buses.add(new Bus(1, "CA0023BP", 23, 14.3));
        buses.add(new Bus(2, "CE1023BV", 25, 26.6));
        busDtos.add(new TripDto.BusDto(1, "CA0023BP"));
        busDtos.add(new TripDto.BusDto(2, "CE1023BV"));
        drivers.add(new Driver(1, "Ivan", "Ivanov", "0878123456", 13.2));
        drivers.add(new Driver(2, "Mitko", "Mitkov", "087812345", 53.2));
        routes.add(new Route(1, "Plovdiv", "Sofia", 14, Duration.parse("PT3H10M"), 130));
        routes.add(new Route(2, "Plovdiv", "Burgas", 18, Duration.parse("PT5H10M"), 250));
        routeDtos.add(new TripDto.RouteDto(1, "Plovdiv", "Sofia", 14, Duration.parse("PT3H10M"), 130));
        routeDtos.add(new TripDto.RouteDto(2, "Plovdiv", "Burgas", 18, Duration.parse("PT5H10M"), 250));


        tripDtos.add(new TripDto(1, LocalDateTime.parse("2021-07-13T21:00:00"),
                LocalDateTime.parse("2021-07-14T00:10:00"), 25, routeDtos.get(0), busDtos.get(1)));
        tripDtos.add(new TripDto(2, LocalDateTime.parse("2023-12-20T08:00:00"),
                LocalDateTime.parse("2023-12-20T13:10:00"), 23, routeDtos.get(1), busDtos.get(0)));

        detailedTripDtos.add(new DetailedTripDto(1, LocalDateTime.parse("2021-07-13T21:00:00"), LocalDateTime.parse("2021" +
                "-07-14T00:10:00"), 25, routes.get(0), buses.get(1), drivers.get(1)));
        detailedTripDtos.add(new DetailedTripDto(2, LocalDateTime.parse("2023-12-20T08:00:00"), LocalDateTime.parse("2023" +
                "-12-20T13:10:00"), 23, routes.get(1), buses.get(0), drivers.get(1)));
    }

    @Test
    void getTripById_corectId_okay() throws Exception {
        when(tripService.getTrip(1)).thenReturn(tripDtos.get(0));

        mockMvc.perform(get("/api/v1/trips/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(tripDtos.get(0))));
    }

    @Test
    void getTrips_noParams_okay() throws Exception {
        when(tripService.getByDepartureDateAndFilters(any(), any(), any(), any())).thenReturn(tripDtos);

        mockMvc.perform(get("/api/v1/trips"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(tripDtos)));
    }

    @Test
    void getTrips_allParams_okay() throws Exception {
        when(tripService.getByDepartureDateAndFilters(LocalDate.of(2022, 11, 3), "Sofia", "Plovdiv", 33)).thenReturn(tripDtos);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("departureDate", LocalDate.of(2022, 11, 3).toString());
        requestParams.add("departureCity", "Sofia");
        requestParams.add("arrivalCity", "Plovdiv");
        requestParams.add("minimumRemainingSeats", "33");

        mockMvc.perform(get("/api/v1/trips").params(requestParams))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(tripDtos)));
    }

    @Test
    void getTripsByDepartureDateGreaterThanEqualsAndFilters_onlyDateParam_okay() throws Exception {
        when(tripService.getByDepartureDateGreaterThanEqualAndFilters(any(), any(), any(), any())).thenReturn(tripDtos);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("minimumDepartureDate", LocalDate.of(2022, 11, 3).toString());

        mockMvc.perform(get("/api/v1/trips/afterDate").params(requestParams))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(tripDtos)));
    }

    @Test
    void getTripsByDepartureDateGreaterThanEqualsAndFilters_allParams_okay() throws Exception {
        when(tripService.getByDepartureDateGreaterThanEqualAndFilters(LocalDate.of(2022, 11, 3), "Sofia", "Plovdiv", 33)).thenReturn(tripDtos);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("minimumDepartureDate", LocalDate.of(2022, 11, 3).toString());
        requestParams.add("departureCity", "Sofia");
        requestParams.add("arrivalCity", "Plovdiv");
        requestParams.add("minimumRemainingSeats", "33");

        mockMvc.perform(get("/api/v1/trips/afterDate").params(requestParams))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(tripDtos)));
    }

    @Test
    void getDetailedTripById_corectId_okay() throws Exception {
        when(tripService.getDetailedTrip(1)).thenReturn(detailedTripDtos.get(0));

        mockMvc.perform(get("/api/v1/detailedTrips/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(detailedTripDtos.get(0))));
    }

    @Test
    void getDetailedTrips_noParams_okay() throws Exception {
        when(tripService.getDetailedTripsByDepartureDateAndFilters(any(), any(), any(), any())).thenReturn(detailedTripDtos);

        mockMvc.perform(get("/api/v1/detailedTrips"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(detailedTripDtos)));
    }

    @Test
    void getDetailedTrips_allParams_okay() throws Exception {
        when(tripService.getDetailedTripsByDepartureDateAndFilters(LocalDate.of(2022, 11, 3), "Sofia", "Plovdiv", 33)).thenReturn(detailedTripDtos);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("departureDate", LocalDate.of(2022, 11, 3).toString());
        requestParams.add("departureCity", "Sofia");
        requestParams.add("arrivalCity", "Plovdiv");
        requestParams.add("minimumRemainingSeats", "33");

        mockMvc.perform(get("/api/v1/detailedTrips").params(requestParams))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(detailedTripDtos)));
    }

    @Test
    void getDetailedTripsByDepartureDateGreaterThanEqualsAndFilters_onlyDateParam_okay() throws Exception {
        when(tripService.getDetailedTripsByDepartureDateGreaterThanEqualAndFilters(any(), any(), any(), any())).thenReturn(detailedTripDtos);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("minimumDepartureDate", LocalDate.of(2022, 11, 3).toString());

        mockMvc.perform(get("/api/v1/detailedTrips/afterDate").params(requestParams))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(detailedTripDtos)));
    }

    @Test
    void getDetailedTripsByDepartureDateGreaterThanEqualsAndFilters_allParams_okay() throws Exception {
        when(tripService.getDetailedTripsByDepartureDateGreaterThanEqualAndFilters(any(), any(), any(), any())).thenReturn(detailedTripDtos);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("minimumDepartureDate", LocalDate.of(2022, 11, 3).toString());
        requestParams.add("departureCity", "Sofia");
        requestParams.add("arrivalCity", "Plovdiv");
        requestParams.add("minimumRemainingSeats", "33");

        mockMvc.perform(get("/api/v1/detailedTrips/afterDate").params(requestParams))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(detailedTripDtos)));
    }

    @Test
    void getDetailedTripByRouteId() throws Exception {
        when(tripService.getDetailedTripsByRoute(1)).thenReturn(detailedTripDtos);

        mockMvc.perform(get("/api/v1/routes/1/detailedTrips"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(detailedTripDtos)));
    }

    @Test
    void getDetailedTripByBusId() throws Exception {
        when(tripService.getDetailedTripsByBus(1)).thenReturn(detailedTripDtos);

        mockMvc.perform(get("/api/v1/buses/1/detailedTrips"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(detailedTripDtos)));
    }

    @Test
    void getDetailedTripByDriverId() throws Exception {
        when(tripService.getDetailedTripsByDriver(1)).thenReturn(detailedTripDtos);

        mockMvc.perform(get("/api/v1/drivers/1/detailedTrips"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(detailedTripDtos)));
    }

    @Test
    void addTrip_trip_isCreatedResponseAndLocationHeader() throws Exception {
        TripRequestDto tripRequestDto = new TripRequestDto(LocalDateTime.of(2022, 10, 13, 0, 0), 1, 1, 1);
        when(tripService.addTrip(tripRequestDto)).thenReturn(2);

        Map<String, Object> body = new HashMap<>();
        body.put("departureDate", "2022-10-13T00:00:00.000");
        body.put("routeID", 1);
        body.put("busID", 1);
        body.put("driverID", 1);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/detailedTrips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/detailedTrips/2"));
    }

    @Test
    void updateTrip_trip_isOkResponse() throws Exception {
        TripRequestDto tripRequestDto = new TripRequestDto(LocalDateTime.of(2022, 10, 13, 0, 0), 1, 1, 1);
        when(tripService.updateTrip(2, tripRequestDto)).thenReturn(2);

        Map<String, Object> body = new HashMap<>();
        body.put("departureDate", "2022-10-13T00:00:00.000");
        body.put("routeID", 1);
        body.put("busID", 1);
        body.put("driverID", 1);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/detailedTrips/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTrip() throws Exception {
        mockMvc.perform(delete("/api/v1/detailedTrips/1"))
                .andExpect(status().isNoContent());
    }
}