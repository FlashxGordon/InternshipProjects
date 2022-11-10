package com.sfa22.ScaleTickets.services;

import com.sfa22.ScaleTickets.data.DailyRevenueRepository;
import com.sfa22.ScaleTickets.data.TicketRepository;
import com.sfa22.ScaleTickets.dtos.DailyRevenueDto;
import com.sfa22.ScaleTickets.entities.*;
import com.sfa22.ScaleTickets.exceptions.InvalidUserInputException;
import com.sfa22.ScaleTickets.exceptions.ResourceMissingException;
import com.sfa22.ScaleTickets.mappers.DailyRevenueMapper;
import com.sfa22.ScaleTickets.services.implementations.DailyRevenueServiceImpl;
import com.sfa22.ScaleTickets.validations.DailyRevenueValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DailyRevenueServiceImplTest {

    @Spy
    DailyRevenueValidation dailyRevenueValidation;

    @Spy
    DailyRevenueMapper mapper;

    @Mock
    DailyRevenueRepository dailyRevenueRepository;

    @Mock
    TicketRepository ticketRepository;

    @InjectMocks
    DailyRevenueServiceImpl dailyRevenueService;

    Integer REMAINING_SEATS, PERCENTAGE, CAPACITY, DISTANCE, SEATS, REVENUE_ID;
    double COST, WAGE, PRICE, DAILY_INCOME;
    String CODE, EMAIL, BUS_PLATE, PHONE, FIRST_NAME, LAST_NAME, ARRIVAL_CITY, DEPARTURE_CITY;
    Duration TRAVEL_DURATION;
    LocalDate EXPIRATION, INCOME_DATE, TARGET_DATE, START_DATE, END_DATE;
    LocalDateTime ARRIVAL, DEPARTURE;
    Route ROUTE;
    Driver DRIVER;
    Bus BUS;
    Trip TRIP;
    Discount DISCOUNT;
    Ticket TICKET;
    DailyRevenue DAILY_REVENUE;
    List<Ticket> TICKETS;
    DailyRevenueDto DAILY_REVENUE_DTO;
    List<DailyRevenue> DAILY_REVENUE_LIST;
    List<Double> REVENUE_SUM_LIST;

    @BeforeEach
    public void setUp() {
        TARGET_DATE = LocalDate.now();
        START_DATE = LocalDate.now();
        END_DATE = LocalDate.now().plusDays(1);
        INCOME_DATE = LocalDate.now();
        DAILY_INCOME = 50.0;
        DEPARTURE_CITY = "CITY";
        ARRIVAL_CITY = "ARRIVALCITY";
        PRICE = 50.0;
        TRAVEL_DURATION = Duration.ofHours(12);
        DISTANCE = 5000;
        FIRST_NAME = "SOMEONE";
        LAST_NAME = "RANDOM";
        PHONE = "234252";
        WAGE = 50;
        BUS_PLATE = "AB2325";
        CAPACITY = 10;
        COST = 100;
        DEPARTURE = LocalDateTime.now();
        ARRIVAL = LocalDateTime.now().plusHours(12);
        REMAINING_SEATS = 10;
        EMAIL = "email@gmail.com";
        PERCENTAGE = 10;
        CODE = "ABCD";
        EXPIRATION = LocalDate.now().plusMonths(2);
        REVENUE_ID = 2;

        ROUTE = new Route(DEPARTURE_CITY, ARRIVAL_CITY, PRICE, TRAVEL_DURATION, DISTANCE);
        DRIVER = new Driver(FIRST_NAME, LAST_NAME, PHONE, WAGE);
        BUS = new Bus(BUS_PLATE, CAPACITY, COST);
        TRIP = new Trip(1, DEPARTURE, ARRIVAL, REMAINING_SEATS, ROUTE, BUS, DRIVER);
        DISCOUNT = new Discount(PERCENTAGE, CODE, EXPIRATION);
        DAILY_REVENUE = new DailyRevenue(DAILY_INCOME, INCOME_DATE);
        SEATS = TRIP.getRemainingSeats();
        TICKET = new Ticket(FIRST_NAME, LAST_NAME, EMAIL, PHONE, PRICE, TRIP, DISCOUNT, SEATS);
        TICKETS = new ArrayList<>();
        TICKETS.add(TICKET);
        DAILY_REVENUE_LIST = new ArrayList<>();
        DAILY_REVENUE_LIST.add(DAILY_REVENUE);
        DAILY_REVENUE_DTO = new DailyRevenueDto(DAILY_INCOME, INCOME_DATE);
        REVENUE_SUM_LIST = new ArrayList<>();
        REVENUE_SUM_LIST.add(DAILY_INCOME);
    }


    @Test
    void updateDailyRevenue_whenRevenueTableIsEmpty_doesItUpdate_ok() {

        when(ticketRepository.findAll()).thenReturn(TICKETS);
        when(dailyRevenueRepository.save(DAILY_REVENUE)).thenReturn(DAILY_REVENUE);

        dailyRevenueService.updateDailyRevenue(TARGET_DATE);

        verify(dailyRevenueRepository).save(DAILY_REVENUE);
    }

    @Test
    void updateDailyRevenue_whenRevenueTableIsNotNull_doesItUpdate_ok() {

        when(dailyRevenueRepository.findByIncomeDate(TARGET_DATE)).thenReturn(Optional.ofNullable(DAILY_REVENUE));
        when(ticketRepository.findAll()).thenReturn(TICKETS);
        when(dailyRevenueRepository.save(DAILY_REVENUE)).thenReturn(DAILY_REVENUE);

        dailyRevenueService.updateDailyRevenue(TARGET_DATE);

        verify(dailyRevenueRepository).save(DAILY_REVENUE);
    }


    @Test
    void updateDailyRevenue_whenRevenueTableIsNotEmpty_doesItUpdate_ok() {
        when(dailyRevenueRepository.findByIncomeDate(TARGET_DATE)).thenReturn(Optional.ofNullable(DAILY_REVENUE));
        when(ticketRepository.findAll()).thenReturn(TICKETS);
        when(dailyRevenueRepository.save(DAILY_REVENUE)).thenReturn(DAILY_REVENUE);

        dailyRevenueService.updateDailyRevenue(TARGET_DATE);

        verify(dailyRevenueRepository).save(DAILY_REVENUE);
    }

    @Test
    void getRevenueById_doesItReturn_ok() {
        when(dailyRevenueRepository.getByRevenueId(REVENUE_ID)).thenReturn(Optional.ofNullable(DAILY_REVENUE));

        DailyRevenueDto dailyRevenue = dailyRevenueService.getByRevenueId(REVENUE_ID);

        assertNotNull(dailyRevenue);
    }

    @Test
    void getAllDailyRevenues_doesItReturnAllRevenues_ok() {
        when(dailyRevenueRepository.findAll()).thenReturn(DAILY_REVENUE_LIST);

        List<DailyRevenueDto> dailyRevenueDtoList = dailyRevenueService.getAllDailyRevenues();

        assertEquals(dailyRevenueDtoList.size(), DAILY_REVENUE_LIST.size());
    }

    @Test
    void getDailyRevenueByDate_doesItReturnRevenueByDate_ok() {
        when(dailyRevenueRepository.findByIncomeDate(TARGET_DATE)).thenReturn(Optional.ofNullable(DAILY_REVENUE));

        DailyRevenueDto foundRevenue = dailyRevenueService.getDailyRevenueByDate(TARGET_DATE);

        assertNotNull(foundRevenue);
    }

    @Test
    void getTotalDailyRevenueInDateRange_notNull_ok() {
        when(dailyRevenueRepository.findByIncomeDateBetween(START_DATE, END_DATE)).thenReturn(DAILY_REVENUE_LIST);

        Double totalRevenueSum =
                dailyRevenueService.getTotalDailyRevenueInDateRange(START_DATE, END_DATE);

        assertNotNull(totalRevenueSum);
    }

    @Test
    void getTotalDailyRevenueInDateRange_sumsCorrectly_ok() {
        when(dailyRevenueRepository.findByIncomeDateBetween(START_DATE, END_DATE)).thenReturn(DAILY_REVENUE_LIST);

        Double totalRevenueSum =
                dailyRevenueService.getTotalDailyRevenueInDateRange(START_DATE, END_DATE);

        assertEquals(totalRevenueSum, DAILY_INCOME);
    }

    @Test
    void getListOfDailyRevenuesInDateRange_doestItRetrieveRevenuesInRange_ok() {
        when(dailyRevenueRepository.findByIncomeDateBetween(START_DATE, END_DATE)).thenReturn(DAILY_REVENUE_LIST);

        List<DailyRevenueDto> dailyRevenueDtoList = dailyRevenueService.getListOfDailyRevenuesInDateRange(START_DATE, END_DATE);

        assertEquals(dailyRevenueDtoList.size(), DAILY_REVENUE_LIST.size());
    }


    @Test
    void getDailyRevenueIncomeByDate_returnsDoubleOfIncome_ok() {


        when(dailyRevenueRepository.findByIncomeDate(TARGET_DATE)).thenReturn(Optional.ofNullable(DAILY_REVENUE));

        Double incomeByDate = dailyRevenueService.getDailyRevenueIncomeByDate(TARGET_DATE);

        assertEquals(incomeByDate, DAILY_INCOME);
    }

    @Test
    void getDailyRevenueIncomeByDate_returnsDoubleOfIncome_notNull() {


        when(dailyRevenueRepository.findByIncomeDate(TARGET_DATE)).thenReturn(Optional.ofNullable(DAILY_REVENUE));

        Double incomeByDate = dailyRevenueService.getDailyRevenueIncomeByDate(TARGET_DATE);

      assertNotNull(DAILY_REVENUE);
    }

    @Test
    void getListOfDailyRevenuesInDateRange_doesItThrow_ok() {

        Throwable runtimeException = assertThrows(InvalidUserInputException.class, () ->
                dailyRevenueService.getListOfDailyRevenuesInDateRange(END_DATE, START_DATE), "The start date of the date range cannot be after the end date.");

        assertTrue(runtimeException.getMessage().contains("The start date of the date "));
    }

    @Test
    void getDailyRevenueIncomeByDate_doesItThrow_ok() {

        Throwable runtimeException = assertThrows(ResourceMissingException.class, () ->
                dailyRevenueService.getDailyRevenueByDate(START_DATE), "No revenue data present for this date");

        assertTrue(runtimeException.getMessage().contains("No revenue data"));
    }
    @Test
    void getRevenueById_doesItThrow_ok() {

        Throwable runtimeException = assertThrows(ResourceMissingException.class, () ->
                dailyRevenueService.getByRevenueId(1), "Revenue entry missing");

        assertTrue(runtimeException.getMessage().contains("Revenue entry missing"));;
    }
}
