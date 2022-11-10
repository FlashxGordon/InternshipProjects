package com.sfa22.ScaleTickets.services;


import com.sfa22.ScaleTickets.data.DiscountCodeRepository;
import com.sfa22.ScaleTickets.data.TicketRepository;
import com.sfa22.ScaleTickets.data.TripRepository;
import com.sfa22.ScaleTickets.dtos.DisplayTicketDto;
import com.sfa22.ScaleTickets.dtos.InputTicketDto;
import com.sfa22.ScaleTickets.entities.*;
import com.sfa22.ScaleTickets.exceptions.InvalidUserInputException;
import com.sfa22.ScaleTickets.exceptions.NoMoreSeatsException;
import com.sfa22.ScaleTickets.exceptions.UnavailableTransportException;
import com.sfa22.ScaleTickets.mappers.TicketMapper;
import com.sfa22.ScaleTickets.services.implementations.EmailServiceImpl;
import com.sfa22.ScaleTickets.services.implementations.TicketServiceImpl;
import com.sfa22.ScaleTickets.smssender.TwilioSmsSender;
import com.sfa22.ScaleTickets.validations.DiscountValidation;
import com.sfa22.ScaleTickets.validations.UserTicketValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceImplTest {


    @Spy
    TicketMapper mapper;

    @Mock
    DiscountValidation validator;

    @Mock
    TwilioSmsSender twilioSmsSender;

    @Mock
    EmailServiceImpl emailService;
    @Mock
    DiscountCodeRepository discountCodeRepository;

    @Mock
    UserTicketValidation validation;

    @Mock
    TripRepository tripRepository;

    @Mock
    TicketRepository ticketRepository;

    @InjectMocks
    TicketServiceImpl underTest;

    int REMAINING_SEATS, PERCENTAGE, CAPACITY, DISTANCE, SEATS, ID;
    double COST, WAGE, PRICE;
    String CODE, EMAIL, EMAIL2, EMAIL3, BUS_PLATE, PHONE, PHONE2, FIRST_NAME, LAST_NAME,
            ARRIVAL_CITY, DEPARTURE_CITY;
    Duration TRAVEL_DURATION;
    LocalDate EXPIRATION, EXPIRATION2;
    LocalDateTime DEPARTURE, ARRIVAL;
    Route route;
    Driver driver;
    Bus bus;
    Trip trip, expiredTrip, noSeatsTrip;
    Discount discount, discount2;
    Ticket ticket, ticket2, ticket3, ticket4, ticket5, ticket6, ticket7, ticket8, bootstrapedTicket;
    Ticket ticketWithoutNumber, ticketWithoutEmail, ticketWithInvalidName, ticketWithInvalidPhone;
    Ticket ticketWithNoEmailAndInvalidPhone;
    InputTicketDto ticketDto, inputTicketDto, inputTicketDto2, inputTicketDto3;

    List<Ticket> tickets;

    @BeforeEach
    public void setUp() {
        DEPARTURE_CITY = "CITY";
        ARRIVAL_CITY = "ARRIVALCITY";
        PRICE = 50.0;
        TRAVEL_DURATION = Duration.ofHours(33);
        DISTANCE = 5000;
        FIRST_NAME = "SOMEONE";
        LAST_NAME = "RANDOM";
        PHONE = "+359878569345";
        WAGE = 50;
        BUS_PLATE = "AB2325";
        CAPACITY = 10;
        COST = 100;
        DEPARTURE = LocalDateTime.now().plusHours(1);
        ARRIVAL = LocalDateTime.now().plusDays(1);
        REMAINING_SEATS = 10;
        EMAIL = "email@gmail.com";
        EMAIL2 = "";
        PHONE2 = "";
        EMAIL3 = "email@scalemail.com";
        PERCENTAGE = 10;
        CODE = "abc12";
        ID = 1;
        EXPIRATION = LocalDate.now().plusMonths(20);
        EXPIRATION2 = LocalDate.now().minusDays(10);

        route = new Route(DEPARTURE_CITY, ARRIVAL_CITY, PRICE, TRAVEL_DURATION, DISTANCE);
        driver = new Driver(FIRST_NAME, LAST_NAME, PHONE, WAGE);
        bus = new Bus(BUS_PLATE, CAPACITY, COST);
        trip = new Trip(1, DEPARTURE, ARRIVAL, REMAINING_SEATS, route, bus, driver);
        expiredTrip = new Trip(1, LocalDateTime.now().minusDays(2), ARRIVAL, REMAINING_SEATS, route, bus, driver);
        noSeatsTrip = new Trip(1, DEPARTURE, ARRIVAL, 0, route, bus, driver);
        discount = new Discount(PERCENTAGE, CODE, EXPIRATION);
        discount2 = new Discount(PERCENTAGE, "kod12", EXPIRATION2);
        SEATS = trip.getRemainingSeats();
        ticket = new Ticket(FIRST_NAME, LAST_NAME, EMAIL, PHONE, PRICE, trip, discount, SEATS);
        ticket2 = new Ticket(FIRST_NAME, LAST_NAME, EMAIL, PHONE, PRICE, trip, SEATS);
        ticket3 = new Ticket(FIRST_NAME, LAST_NAME, EMAIL, PHONE, PRICE, trip, discount2, SEATS);
        ticket4 = new Ticket(ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE, PRICE, trip, discount2, SEATS);
        ticket5 = new Ticket(ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE, PRICE, trip, SEATS);
        ticket6 = new Ticket(ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE, PRICE, trip, discount, SEATS);
        ticket7 = new Ticket(FIRST_NAME, LAST_NAME, EMAIL2, PHONE, PRICE, trip, discount, SEATS);
        ticket8 = new Ticket(FIRST_NAME, LAST_NAME, EMAIL, PHONE2, PRICE, trip, discount, SEATS);
        bootstrapedTicket = new Ticket(FIRST_NAME, LAST_NAME, EMAIL3, PHONE, PRICE, trip, discount2, SEATS);
        ticketWithoutEmail = new Ticket(FIRST_NAME, LAST_NAME, EMAIL2, PHONE, PRICE, trip, discount2, SEATS);
        ticketWithoutNumber = new Ticket(FIRST_NAME, LAST_NAME, EMAIL, PHONE2, PRICE, trip, discount2, SEATS);
        ticketWithInvalidName = new Ticket(FIRST_NAME, "", EMAIL, PHONE2, PRICE, trip, discount2, SEATS);
        ticketWithInvalidPhone = new Ticket(FIRST_NAME, LAST_NAME, EMAIL, "32252", PRICE, trip, discount2, SEATS);
        ticketWithNoEmailAndInvalidPhone = new Ticket(FIRST_NAME, LAST_NAME, "", "32252", PRICE, trip, discount2, SEATS);
        tickets = new ArrayList<>();
        tickets.add(ticket);
        ticketDto = new InputTicketDto(FIRST_NAME + " " + LAST_NAME, EMAIL, PHONE, 1, CODE);
        inputTicketDto = new InputTicketDto(FIRST_NAME + " " + LAST_NAME, EMAIL, PHONE, 1, "");
        inputTicketDto2 = new InputTicketDto(FIRST_NAME + " " + LAST_NAME, EMAIL, PHONE, 1, discount2.getCode());
        inputTicketDto3 = new InputTicketDto(FIRST_NAME + " " + LAST_NAME, EMAIL, PHONE, 1, discount2.getCode());


    }

    @Test
    void getAllTickets_doesItReturnAllTickets_okay() {

        when(ticketRepository.findAll()).thenReturn(tickets);

        List<DisplayTicketDto> ticketDtoList = underTest.getAllTickets();

        assertEquals(ticketDtoList.size(), tickets.size());

    }

    @Test
    void getById_doesItReturnTicketById_okay() {

        when(ticketRepository.findById(1)).
                thenReturn(Optional.ofNullable(ticket));

        Optional<DisplayTicketDto> foundTicket = underTest.getById(1);

        assertNotNull(foundTicket);

    }

    @Test
    void getAllTicketsByFirstNameAndLastName_doesItReturnTickets_okay() {

        when(ticketRepository.findTicketsByFirstNameAndLastName(FIRST_NAME, LAST_NAME)).
                thenReturn(tickets);

        List<DisplayTicketDto> ticketDtoList =
                underTest.getAllTicketsByFirstNameAndLastName(FIRST_NAME, LAST_NAME);

        assertEquals(ticketDtoList.size(), tickets.size());
    }

    @Test
    void getAllTicketsByFirstName_doesItReturnTickets_okay() {

        when(ticketRepository.findTicketsByFirstName(FIRST_NAME)).
                thenReturn(tickets);

        List<DisplayTicketDto> ticketDtoList =
                underTest.getAllTicketsByFirstName(FIRST_NAME);

        assertEquals(ticketDtoList.size(), tickets.size());

    }

    @Test
    void getAllTicketsByLastName_doesItReturnTickets_okay() {

        when(ticketRepository.findTicketsByLastName(LAST_NAME)).
                thenReturn(tickets);

        List<DisplayTicketDto> ticketDtoList =
                underTest.getAllTicketsByLastName(LAST_NAME);

        assertEquals(ticketDtoList.size(), tickets.size());

    }

    @Test
    void getAllTicketsByPrice_doesItReturnTickets_okay() {
        when(ticketRepository.findTicketsByTicketPrice(PRICE)).
                thenReturn(tickets);

        List<DisplayTicketDto> ticketDtoList =
                underTest.getAllTicketsByPrice(PRICE);

        assertEquals(ticketDtoList.size(), tickets.size());

    }

    @Test
    void getAllTicketsByDiscountCode_doesItReturnTickets_okay() {
        List<Ticket> tickets = new ArrayList<>();

        tickets.add(ticket);

        when(ticketRepository.findAllByDiscount_Code(CODE)).
                thenReturn(tickets);

        List<DisplayTicketDto> ticketDtoList =
                underTest.getAllTicketsByDiscountCode(CODE);

        assertEquals(ticketDtoList.size(), tickets.size());

    }

    @Test
    void getAllTicketsByDepartureCity_doesItReturnTickets_okay() {
        when(ticketRepository.findAllByTrip_Route_DepartureCity(DEPARTURE_CITY)).
                thenReturn(tickets);

        List<DisplayTicketDto> ticketDtoList =
                underTest.getAllTicketsByDepartureCity(DEPARTURE_CITY);

        assertEquals(ticketDtoList.size(), tickets.size());

    }

    @Test
    void getAllTicketsByArrivalCity_doesItReturnTickets_okay() {
        when(ticketRepository.findAllByTrip_Route_ArrivalCity(ARRIVAL_CITY)).
                thenReturn(tickets);

        List<DisplayTicketDto> ticketDtoList =
                underTest.getAllTicketsByArrivalCity(ARRIVAL_CITY);

        assertEquals(ticketDtoList.size(), tickets.size());

    }

    @Test
    void getAllTicketsByArrivalDateTime_doesItReturnTickets_okay() {
        when(ticketRepository.findAllByTrip_ArrivalDate(ARRIVAL)).
                thenReturn(tickets);

        List<DisplayTicketDto> ticketDtoList =
                underTest.getAllTicketsByArrivalDateTime(ARRIVAL);

        assertEquals(ticketDtoList.size(), tickets.size());

    }

    @Test
    void getAllTicketsByDepartureDateTime_doesItReturnTickets_okay() {
        when(ticketRepository.findAllByTrip_DepartureDate(DEPARTURE)).
                thenReturn(tickets);

        List<DisplayTicketDto> ticketDtoList =
                underTest.getAllTicketsByDepartureDateTime(DEPARTURE);

        assertEquals(ticketDtoList.size(), tickets.size());

    }

    @Test
    void getAllTicketsByDepartureDateLessThan_doesItReturnTickets_okay() {

        when(ticketRepository.findByDepartureDateLessThan(DEPARTURE)).
                thenReturn(tickets);

        List<DisplayTicketDto> ticketDtoList =
                underTest.getAllTicketsByDepartureDateLessThan(DEPARTURE);

        assertEquals(ticketDtoList.size(), tickets.size());

    }

    @Test
    void getAllTicketsByDepartureDateGreaterThanEqual_doesItReturnTickets_okay() {

        when(ticketRepository.findByDepartureDateGreaterThanEqual(DEPARTURE)).
                thenReturn(tickets);

        List<DisplayTicketDto> ticketDtoList =
                underTest.getAllTicketsByDepartureDateGreaterThanEqual(DEPARTURE);

        assertEquals(ticketDtoList.size(), tickets.size());

    }

    @Test
    void getAllTicketsByArrivalLessThan_doesItReturnTickets_okay() {
        when(ticketRepository.findByArrivalLessThan(ARRIVAL)).
                thenReturn(tickets);

        List<DisplayTicketDto> ticketDtoList =
                underTest.getAllTicketsByArrivalLessThan(ARRIVAL);

        assertEquals(ticketDtoList.size(), tickets.size());

    }

    @Test
    void getAllTicketsByArrivalDateGreaterThanEqual_doesItReturnTickets_okay() {

        when(ticketRepository.findByArrivalDateGreaterThanEqual(ARRIVAL)).
                thenReturn(tickets);

        List<DisplayTicketDto> ticketDtoList =
                underTest.getAllTicketsByArrivalDateGreaterThanEqual(ARRIVAL);

        assertEquals(ticketDtoList.size(), tickets.size());

    }

    @Test
    void saveTicket_doesItThrowUnavailableTransportException_okay() {

        when(tripRepository.findById(ticket.getTrip().getTripId())).thenReturn(Optional.ofNullable(expiredTrip));

        InputTicketDto dto = mapper.mapTicketToInputTicketDto(ticket);

        when(validation.isUserNameValid(dto.getClientName())).thenReturn(true);

        Assertions.assertThrows(UnavailableTransportException.class, () -> underTest.saveTicket(dto));


    }


    @Test
    void saveTicket_doesItThrowInvalidUserInputExceptionByName_okay() {


        InputTicketDto dto = mapper.mapTicketToInputTicketDto(ticketWithInvalidName);

        when(validation.isUserNameValid(dto.getClientName())).thenReturn(false);


        Assertions.assertThrows(InvalidUserInputException.class, () -> underTest.saveTicket(dto));


    }

    @Test
    void saveTicket_doesItThrowInvalidUserInputExceptionByPhoneNumber_okay() {

        when(tripRepository.findById(ticketWithInvalidPhone.getTrip().getTripId())).thenReturn(Optional.ofNullable(trip));
        when(validator.isValid(ticketWithInvalidPhone.getDiscount().getCode())).thenReturn(true);
        when(discountCodeRepository.findByCode("kod12")).thenReturn(Optional.ofNullable(discount2));


        InputTicketDto dto = mapper.mapTicketToInputTicketDto(ticketWithInvalidPhone);


        when(validation.isUserNameValid(dto.getClientName())).thenReturn(true);

        Assertions.assertThrows(InvalidUserInputException.class, () -> underTest.saveTicket(dto));


    }

    @Test
    void saveTicket_doesItThrowInvalidUserInputExceptionByPhoneNumberAndNoEmail_okay() {

        when(tripRepository.findById(ticketWithNoEmailAndInvalidPhone.getTrip().getTripId())).thenReturn(Optional.ofNullable(trip));
        when(validator.isValid(ticketWithNoEmailAndInvalidPhone.getDiscount().getCode())).thenReturn(true);
        when(discountCodeRepository.findByCode("kod12")).thenReturn(Optional.ofNullable(discount2));


        InputTicketDto dto = mapper.mapTicketToInputTicketDto(ticketWithNoEmailAndInvalidPhone);


        when(validation.isUserNameValid(dto.getClientName())).thenReturn(true);

        Assertions.assertThrows(InvalidUserInputException.class, () -> underTest.saveTicket(dto));


    }

    @Test
    void saveTicket_doesItThrowInvalidUserInputException_okay() throws MessagingException {

        when(tripRepository.findById(ticket3.getTrip().getTripId())).thenReturn(Optional.ofNullable(trip));

        InputTicketDto dto = mapper.mapTicketToInputTicketDto(ticket3);

        when(validation.isUserNameValid(dto.getClientName())).thenReturn(true);

        Assertions.assertThrows(InvalidUserInputException.class, () -> underTest.saveTicket(dto));


    }

    @Test
    void saveTicket_doesItThrowNoSeatsException_okay() {

        when(tripRepository.findById(ticket.getTrip().getTripId())).thenReturn(Optional.ofNullable(noSeatsTrip));

        InputTicketDto dto = mapper.mapTicketToInputTicketDto(ticket);

        when(validation.isUserNameValid(dto.getClientName())).thenReturn(true);

        Assertions.assertThrows(NoMoreSeatsException.class, () -> underTest.saveTicket(dto));


    }

    @Test
    void saveTicket_doesItSaveItWithDiscount_okay() throws MessagingException {

        when(tripRepository.findById(ticket.getTrip().getTripId())).thenReturn(Optional.ofNullable(trip));
        when(validator.isValid(ticket.getDiscount().getCode())).thenReturn(true);
        when(discountCodeRepository.findByCode(ticket.getDiscount().getCode())).thenReturn(Optional.ofNullable(discount));
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        ticket.setTicketPrice(PRICE - (PRICE * discount.getPercentage() / 100));
        InputTicketDto ticketDtok = mapper.mapTicketToInputTicketDto(ticket);

        when(validation.isUserNameValid(ticketDtok.getClientName())).thenReturn(true);
        when(validation.isUserPhoneNumberValid(ticketDtok.getPhoneNumber())).thenReturn(true);


        DisplayTicketDto displayTicketDto = mapper.mapTicketToDisplayTicketDto(ticket);

        emailService.sendEmail(displayTicketDto, EMAIL);

        underTest.saveTicket(ticketDtok);

        verify(ticketRepository).save(ticket);

    }

    @Test
    void saveTicket_doesItSaveItWithRegularPrice_okay() throws MessagingException {

        when(tripRepository.findById(ticket2.getTrip().getTripId())).thenReturn(Optional.ofNullable(trip));
        when(ticketRepository.save(ticket2)).thenReturn(ticket2);
        DisplayTicketDto displayTicketDto = mapper.mapTicketToDisplayTicketDto(ticket2);

        when(validation.isUserNameValid(inputTicketDto.getClientName())).thenReturn(true);
        when(validation.isUserPhoneNumberValid(inputTicketDto.getPhoneNumber())).thenReturn(true);


        emailService.sendEmail(displayTicketDto, EMAIL);
        twilioSmsSender.sendSms(ticketDto, displayTicketDto);

        underTest.saveTicket(inputTicketDto);

        verify(ticketRepository).save(ticket2);

    }


    @Test
    void saveTicket_doesItSaveItWithExpiredDiscountCode_okay() throws MessagingException {

        when(tripRepository.findById(ticket3.getTrip().getTripId())).thenReturn(Optional.ofNullable(trip));
        when(validator.isValid(ticket3.getDiscount().getCode())).thenReturn(true);
        when(discountCodeRepository.findByCode("kod12")).thenReturn(Optional.ofNullable(discount2));
        when(ticketRepository.save(ticket3)).thenReturn(ticket3);
        DisplayTicketDto displayTicketDto = mapper.mapTicketToDisplayTicketDto(ticket3);

        when(validation.isUserNameValid(inputTicketDto2.getClientName())).thenReturn(true);
        when(validation.isUserPhoneNumberValid(inputTicketDto2.getPhoneNumber())).thenReturn(true);

        emailService.sendEmail(displayTicketDto, EMAIL);

        underTest.saveTicket(inputTicketDto2);

        verify(ticketRepository).save(ticket3);


    }

    @Test
    void saveTicket_doesItSaveBootstrappedTicket_okay() throws MessagingException {

        when(tripRepository.findById(bootstrapedTicket.getTrip().getTripId())).thenReturn(Optional.ofNullable(trip));
        when(validator.isValid(bootstrapedTicket.getDiscount().getCode())).thenReturn(true);
        when(discountCodeRepository.findByCode("kod12")).thenReturn(Optional.ofNullable(discount2));
        when(ticketRepository.save(bootstrapedTicket)).thenReturn(bootstrapedTicket);

        DisplayTicketDto displayTicketDto = mapper.mapTicketToDisplayTicketDto(bootstrapedTicket);
        InputTicketDto dto = mapper.mapTicketToInputTicketDto(bootstrapedTicket);

        when(validation.isUserNameValid(dto.getClientName())).thenReturn(true);


        emailService.sendEmail(displayTicketDto, EMAIL3);

        underTest.saveTicket(dto);

        verify(ticketRepository).save(bootstrapedTicket);


    }

    @Test
    void saveTicket_doesItSaveWithoutPhoneNumber_okay() throws MessagingException {

        when(tripRepository.findById(ticketWithoutNumber.getTrip().getTripId())).thenReturn(Optional.ofNullable(trip));
        when(validator.isValid(ticketWithoutNumber.getDiscount().getCode())).thenReturn(true);
        when(discountCodeRepository.findByCode("kod12")).thenReturn(Optional.ofNullable(discount2));
        when(ticketRepository.save(ticketWithoutNumber)).thenReturn(ticketWithoutNumber);

        DisplayTicketDto displayTicketDto = mapper.mapTicketToDisplayTicketDto(ticketWithoutNumber);
        InputTicketDto dto = mapper.mapTicketToInputTicketDto(ticketWithoutNumber);

        when(validation.isUserNameValid(dto.getClientName())).thenReturn(true);


        emailService.sendEmail(displayTicketDto, EMAIL);

        underTest.saveTicket(dto);

        verify(ticketRepository).save(ticketWithoutNumber);


    }

    @Test
    void saveTicket_doesItSaveWithoutEmail_okay() throws MessagingException {

        when(tripRepository.findById(ticketWithoutEmail.getTrip().getTripId())).thenReturn(Optional.ofNullable(trip));
        when(validator.isValid(ticketWithoutEmail.getDiscount().getCode())).thenReturn(true);
        when(discountCodeRepository.findByCode("kod12")).thenReturn(Optional.ofNullable(discount2));
        when(ticketRepository.save(ticketWithoutEmail)).thenReturn(ticketWithoutEmail);

        DisplayTicketDto displayTicketDto = mapper.mapTicketToDisplayTicketDto(ticketWithoutEmail);
        InputTicketDto dto = mapper.mapTicketToInputTicketDto(ticketWithoutEmail);

        when(validation.isUserNameValid(dto.getClientName())).thenReturn(true);
        when(validation.isUserPhoneNumberValid(dto.getPhoneNumber())).thenReturn(true);

        twilioSmsSender.sendSms(dto, displayTicketDto);

        underTest.saveTicket(dto);

        verify(ticketRepository).save(ticketWithoutEmail);


    }


}
