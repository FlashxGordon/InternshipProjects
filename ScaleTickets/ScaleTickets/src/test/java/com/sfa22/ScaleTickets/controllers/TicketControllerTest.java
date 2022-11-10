package com.sfa22.ScaleTickets.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sfa22.ScaleTickets.dtos.DisplayTicketDto;
import com.sfa22.ScaleTickets.dtos.InputTicketDto;
import com.sfa22.ScaleTickets.entities.*;
import com.sfa22.ScaleTickets.mappers.TicketMapper;
import com.sfa22.ScaleTickets.services.implementations.TicketServiceImpl;
import org.junit.jupiter.api.BeforeAll;
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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TicketController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class TicketControllerTest {


    @MockBean
    TicketServiceImpl ticketServiceImpl;


    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper().
            registerModule(new JavaTimeModule()).setDateFormat(new SimpleDateFormat());


    static String FIRST_NAME, LAST_NAME, ARRIVAL_CITY, DEPARTURE_CITY, BUS_PLATE, EMAIL,
            PHONE_NUMBER, CODE;
    static LocalDateTime DEPARTURE, ARRIVAL, PAST_DEPARTURE;
    static int ID, SEAT, DISTANCE, CAPACITY, REMAINING_SEATS, PERCENTAGE;

    static LocalDate EXPIRATION;

    static Duration TRAVEL_DURATION;
    static double PRICE, WAGE;

    static Route route;
    static Driver driver;
    static Bus bus;
    static Trip trip;
    static Discount discount;
    static Ticket ticket;

    static TicketMapper mapper;

    static InputTicketDto inputTicketDto;
    static DisplayTicketDto displayTicketDto;
    static List<DisplayTicketDto> displayTicketDtoList;

    @BeforeAll
    public static void setUp() throws Exception {
        ID = 1;
        DEPARTURE_CITY = "CITY";
        ARRIVAL_CITY = "ARRIVALCITY";
        PRICE = 50.0;
        PAST_DEPARTURE = LocalDateTime.now().minusDays(2);
        TRAVEL_DURATION = Duration.ofHours(33);
        DISTANCE = 5000;
        FIRST_NAME = "SOMEONE";
        LAST_NAME = "RANDOM";
        PHONE_NUMBER = "234252";
        WAGE = 50;
        BUS_PLATE = "AB2325";
        CAPACITY = 10;
        PRICE = 45.00;
        DEPARTURE = LocalDateTime.now().plusHours(1);
        ARRIVAL = LocalDateTime.now().plusDays(1);
        REMAINING_SEATS = 10;
        EMAIL = "email@gmail.com";
        PERCENTAGE = 10;
        CODE = "ABCD";
        EXPIRATION = LocalDate.now().plusMonths(20);
        route = new Route(DEPARTURE_CITY, ARRIVAL_CITY, PRICE, TRAVEL_DURATION, DISTANCE);
        driver = new Driver(FIRST_NAME, LAST_NAME, PHONE_NUMBER, WAGE);
        bus = new Bus(BUS_PLATE, CAPACITY, PRICE);
        trip = new Trip(1, DEPARTURE, ARRIVAL, REMAINING_SEATS, route, bus, driver);
        discount = new Discount(PERCENTAGE, CODE, EXPIRATION);
        SEAT = trip.getRemainingSeats();
        ticket = new Ticket(FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, PRICE, trip, discount, SEAT);

        mapper = new TicketMapper();
        displayTicketDto = mapper.mapTicketToDisplayTicketDto(ticket);

        inputTicketDto = mapper.mapTicketToInputTicketDto(ticket);


        displayTicketDtoList = new ArrayList<>();

        displayTicketDtoList.add(displayTicketDto);

    }

    @Test
    void getAllTickets_isStatusOk_okay() throws Exception {
        when(ticketServiceImpl.getAllTickets()).thenReturn(displayTicketDtoList);

        mockMvc.perform(get("/api/v1/tickets"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(displayTicketDtoList)));

    }

    @Test
    void getTicketById_isStatusOk_okay() throws Exception {
        when(ticketServiceImpl.getById(1)).thenReturn(Optional.ofNullable(displayTicketDto));

        mockMvc.perform(get("/api/v1/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(displayTicketDto)));

    }

    @Test
    void getAllTicketsByFirstNameAndLastName_isStatusOk_okay() throws Exception {
        when(ticketServiceImpl.getAllTicketsByFirstNameAndLastName(FIRST_NAME, LAST_NAME))
                .thenReturn(displayTicketDtoList);

        mockMvc.perform(get("/api/v1/tickets")
                        .param("firstName", FIRST_NAME)
                        .param("lastName", LAST_NAME))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(displayTicketDtoList)));

    }


    @Test
    void getAllTicketsByFirstName_isStatusOk_okay() throws Exception {
        when(ticketServiceImpl.getAllTicketsByFirstName(FIRST_NAME))
                .thenReturn(displayTicketDtoList);

        mockMvc.perform(get("/api/v1/tickets")
                        .param("firstName", FIRST_NAME))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(displayTicketDtoList)));

    }

    @Test
    void getAllTicketsByLastName_isStatusOk_okay() throws Exception {
        when(ticketServiceImpl.getAllTicketsByLastName(LAST_NAME))
                .thenReturn(displayTicketDtoList);

        mockMvc.perform(get("/api/v1/tickets")
                        .param("lastName", LAST_NAME))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(displayTicketDtoList)));

    }

    @Test
    void getAllTicketsByPrice_isStatusOk_okay() throws Exception {
        when(ticketServiceImpl.getAllTicketsByPrice(PRICE))
                .thenReturn(displayTicketDtoList);

        mockMvc.perform(get("/api/v1/tickets")
                        .param("price", String.valueOf(PRICE)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(displayTicketDtoList)));

    }

    @Test
    void getAllTicketsByArrivalCity_isStatusOk_okay() throws Exception {
        when(ticketServiceImpl.getAllTicketsByArrivalCity(ARRIVAL_CITY))
                .thenReturn(displayTicketDtoList);

        mockMvc.perform(get("/api/v1/tickets")
                        .param("arrivalCity", ARRIVAL_CITY))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(displayTicketDtoList)));

    }

    @Test
    void getAllTicketsByArrivalDateTime_isStatusOk_okay() throws Exception {
        when(ticketServiceImpl.getAllTicketsByArrivalDateTime(ARRIVAL))
                .thenReturn(displayTicketDtoList);

        mockMvc.perform(get("/api/v1/tickets")
                        .param("arrival", String.valueOf(ARRIVAL)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(displayTicketDtoList)));

    }

    @Test
    void getAllTicketsByDepartureCity_isStatusOk_okay() throws Exception {
        when(ticketServiceImpl.getAllTicketsByDepartureCity(DEPARTURE_CITY))
                .thenReturn(displayTicketDtoList);

        mockMvc.perform(get("/api/v1/tickets")
                        .param("departureCity", DEPARTURE_CITY))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(displayTicketDtoList)));

    }

    @Test
    void getAllByDiscountCode_isStatusOk_okay() throws Exception {
        when(ticketServiceImpl.getAllTicketsByDiscountCode(CODE))
                .thenReturn(displayTicketDtoList);

        mockMvc.perform(get("/api/v1/tickets")
                        .param("code", CODE))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(displayTicketDtoList)));

    }

    @Test
    void getAllTicketsByDepartureDateTime_isStatusOk_okay() throws Exception {
        when(ticketServiceImpl.getAllTicketsByDepartureDateTime(DEPARTURE))
                .thenReturn(displayTicketDtoList);

        mockMvc.perform(get("/api/v1/tickets")
                        .param("departure", String.valueOf(DEPARTURE)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(displayTicketDtoList)));

    }

    @Test
    void getAllTicketsByDepartureDateTimeLessThan_isStatusOk_okay() throws Exception {
        when(ticketServiceImpl.getAllTicketsByDepartureDateLessThan(DEPARTURE))
                .thenReturn(displayTicketDtoList);

        mockMvc.perform(get("/api/v1/tickets/beforeDepartureDateTime")
                        .param("date", String.valueOf(DEPARTURE)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(displayTicketDtoList)));

    }

    @Test
    void getAllTicketsByDepartureDateGreaterThanEqual_isStatusOk_okay() throws Exception {
        when(ticketServiceImpl.getAllTicketsByDepartureDateGreaterThanEqual(PAST_DEPARTURE))
                .thenReturn(displayTicketDtoList);

        mockMvc.perform(get("/api/v1/tickets/afterOnDepartureDateTime")
                        .param("date", String.valueOf(PAST_DEPARTURE)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(displayTicketDtoList)));

    }


    @Test
    void getAllTicketsByArrivalDateLessThan_isStatusOk_okay() throws Exception {
        when(ticketServiceImpl.getAllTicketsByArrivalLessThan(ARRIVAL))
                .thenReturn(displayTicketDtoList);

        mockMvc.perform(get("/api/v1/tickets/beforeArrivalDate")
                        .param("date", String.valueOf(ARRIVAL)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(displayTicketDtoList)));

    }

    @Test
    void getAllTicketsByArrivalDateGreaterThanEqual_isStatusOk_okay() throws Exception {
        when(ticketServiceImpl.getAllTicketsByArrivalDateGreaterThanEqual(ARRIVAL))
                .thenReturn(displayTicketDtoList);

        mockMvc.perform(get("/api/v1/tickets/afterOnArrivalDate")
                        .param("date", String.valueOf(ARRIVAL)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(displayTicketDtoList)));

    }

    @Test
    void saveTicket_isStatusCreated_okay() throws Exception {
        when(ticketServiceImpl.saveTicket(inputTicketDto))
                .thenReturn(ID);
        Map<String, Object> body = new HashMap<>();
        body.put("clientName", FIRST_NAME + " " + LAST_NAME);
        body.put("email", EMAIL);
        body.put("phoneNumber", PHONE_NUMBER);
        body.put("tripId", trip.getTripId());
        body.put("discountCode", CODE);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


    }


}