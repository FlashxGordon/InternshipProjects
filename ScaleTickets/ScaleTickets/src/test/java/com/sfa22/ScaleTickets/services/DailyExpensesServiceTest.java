package com.sfa22.ScaleTickets.services;

import com.sfa22.ScaleTickets.data.DailyExpensesRepository;
import com.sfa22.ScaleTickets.data.TripRepository;
import com.sfa22.ScaleTickets.dtos.DailyExpensesDto;
import com.sfa22.ScaleTickets.entities.*;
import com.sfa22.ScaleTickets.enums.PricePerKm;
import com.sfa22.ScaleTickets.enums.SoftwareCost;
import com.sfa22.ScaleTickets.mappers.DailyExpensesMapper;
import com.sfa22.ScaleTickets.services.implementations.BusServiceImpl;
import com.sfa22.ScaleTickets.services.implementations.DailyExpensesServiceImpl;
import com.sfa22.ScaleTickets.services.interfaces.BusService;
import com.sfa22.ScaleTickets.services.interfaces.DailyRevenueService;
import com.sfa22.ScaleTickets.services.interfaces.DriverService;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DailyExpensesServiceTest {

    @Mock
    private DailyExpensesRepository dailyExpensesRepository;

    @Spy
    private DailyExpensesMapper dailyExpensesMapper;

    @Mock
    private BusServiceImpl busService;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private DailyRevenueService dailyRevenueService;

    @Mock
    private DriverService driverService;

    @InjectMocks
    private DailyExpensesServiceImpl dailyExpensesService;

    private int dailyExpensesID;

    private int tripID;

    private int remainingSeats;

    private double fixCost;

    private double fuelCost;

    private double softwareCost;

    private double distance;

    private double dailyExpenseSum;

    private double revenueForSpecificDate;

    private double dailyCostOfBuses;

    private double dailyWagesOfDrivers;

    private double expectedExpenseResult;

    private DailyExpenses dailyExpense;

    private Trip trip;

    private Route route;

    private Driver driver;

    private Bus bus;

    private List<DailyExpenses> testListOfDailyExpenses;

    private List<Trip> testListOfTrips;

    private LocalDate date;

    private LocalDate beforeDate;

    private LocalDate afterDate;

    private LocalDateTime departureDate;

    private LocalDateTime arrivalDate;

    @BeforeEach
    public void setUp() {

        date = LocalDate.now();

        beforeDate = date.plusDays(1);

        afterDate = date.minusDays(1);

        departureDate = LocalDateTime.now().plusDays(3);

        arrivalDate = LocalDateTime.now().plusDays(4);

        distance = 500.00;

        remainingSeats = 35;

        revenueForSpecificDate = 500.00;

        route = new Route(1, "Plovdiv", "Varna", 45.55, Duration.ofHours(1),
                500);

        bus = new Bus(1, "PB5555", 44, 100.00);

        driver = new Driver(1, "Test", "Test", "+3590000000", 50.00);

        dailyExpensesID = 1;

        tripID = 1;

        fixCost = 500.00;

        fuelCost = 600.00;

        softwareCost = 400.00;

        dailyCostOfBuses = 1500.00;

        dailyWagesOfDrivers = 2000;

        dailyExpenseSum = fixCost + fuelCost + softwareCost;

        dailyExpense = new DailyExpenses(dailyExpensesID, fixCost, fuelCost, softwareCost, afterDate);

        trip = new Trip(tripID, departureDate, arrivalDate, remainingSeats, route, bus, driver);

        testListOfDailyExpenses = new ArrayList<>();

        testListOfTrips = new ArrayList<>();

        testListOfDailyExpenses.add(dailyExpense);

        testListOfTrips.add(trip);

        expectedExpenseResult = dailyCostOfBuses + dailyWagesOfDrivers +
                revenueForSpecificDate * SoftwareCost.SOFTWARE_COST.getPricePerKm() +
                route.getDistance() * PricePerKm.PRICE_PER_KM.getPricePerKm();
    }

    @Test
    void findByExpenseDate_doesItReturnMappedObject_okay() {

        when(dailyExpensesRepository.findByExpenseDate(date)).thenReturn(Optional.ofNullable(dailyExpense));

        DailyExpensesDto dailyExpensesDto = dailyExpensesService.findByExpenseDate(date);

        assertEquals(dailyExpensesDto.getClass(), DailyExpensesDto.class);
    }

    @Test
    void findByExpenseDate_doesItReturnObject_okay() {

        when(dailyExpensesRepository.findByExpenseDate(date)).thenReturn(Optional.ofNullable(dailyExpense));

        DailyExpensesDto dailyExpensesDto = dailyExpensesService.findByExpenseDate(date);

        assertNotNull(dailyExpensesDto);
    }

    @Test
    void getByExpenseDateGreaterThan_doesItReturnMappedObjects_okay() {

        when(dailyExpensesRepository.findByExpenseDateGreaterThan(afterDate)).thenReturn(testListOfDailyExpenses);

        List<DailyExpensesDto> resultList = dailyExpensesService.getByExpenseDateGreaterThan(afterDate);

        assertEquals(resultList.get(0).getClass(), DailyExpensesDto.class);
    }

    @Test
    void getByExpenseDateGreaterThan_doesItReturnNotEmptyList_okay() {

        when(dailyExpensesRepository.findByExpenseDateGreaterThan(beforeDate)).thenReturn(testListOfDailyExpenses);

        List<DailyExpensesDto> resultList = dailyExpensesService.getByExpenseDateGreaterThan(beforeDate);

        assertNotNull(resultList);
    }

    @Test
    void getByExpenseDateBefore_doesItReturnMappedObjects_okay() {

        when(dailyExpensesRepository.findByExpenseDateBefore(beforeDate)).thenReturn(testListOfDailyExpenses);

        List<DailyExpensesDto> resultList = dailyExpensesService.getByExpenseDateBefore(beforeDate);

        assertEquals(resultList.get(0).getClass(), DailyExpensesDto.class);
    }

    @Test
    void getByExpenseDateBefore_doesItReturnNotEmptyList_okay() {

        when(dailyExpensesRepository.findByExpenseDateBefore(beforeDate)).thenReturn(testListOfDailyExpenses);

        List<DailyExpensesDto> resultList = dailyExpensesService.getByExpenseDateBefore(date.plusDays(1));

        assertNotNull(resultList);
    }

    @Test
    void getByExpenseDateBetween_doesItReturnMappedObjects_okay() {

        when(dailyExpensesRepository.findByExpenseDateBetween(afterDate, beforeDate)).thenReturn(testListOfDailyExpenses);

        List<DailyExpensesDto> resultList = dailyExpensesService.getByExpenseDateBetween(afterDate, beforeDate);

        assertEquals(resultList.get(0).getClass(), DailyExpensesDto.class);
    }

    @Test
    void getByExpenseDateBetween_doesItReturnNotEmptyList_okay() {

        when(dailyExpensesRepository.findByExpenseDateBetween(afterDate, beforeDate)).thenReturn(testListOfDailyExpenses);

        List<DailyExpensesDto> resultList = dailyExpensesService.getByExpenseDateBetween(afterDate, beforeDate);

        assertNotNull(resultList);
    }

    @Test
    void calculateDailyExpensesBetween_doesItCalculate_okay() {

        when(dailyExpensesRepository.findByExpenseDateBetween(afterDate, beforeDate)).thenReturn(testListOfDailyExpenses);

        double result = dailyExpensesService.calculateDailyExpensesBetween(afterDate, beforeDate);

        assertNotEquals(0.0, result);
    }

    @Test
    void calculateDailyExpensesBetween_doesItCalculateCorrectly_okay() {

        when(dailyExpensesRepository.findByExpenseDateBetween(afterDate, beforeDate)).thenReturn(testListOfDailyExpenses);

        double result = dailyExpensesService.calculateDailyExpensesBetween(afterDate, beforeDate);

        assertEquals(dailyExpenseSum, result);
    }

    @Test
    void calculateDailyExpensesForSpecificDate_doesItCalculate_okay() {

        when(busService.getDailyCostOfAllBuses()).thenReturn(dailyCostOfBuses);

        when(driverService.calculateDailyWage()).thenReturn(dailyWagesOfDrivers);

        when(tripRepository.findByDepartureDate(afterDate)).thenReturn(testListOfTrips);

        when(dailyRevenueService.getDailyRevenueIncomeByDate(afterDate)).thenReturn(revenueForSpecificDate);

        when(dailyExpensesRepository.findAll()).thenReturn(testListOfDailyExpenses);

        when(dailyExpensesRepository.findByExpenseDate(afterDate)).thenReturn(Optional.ofNullable(dailyExpense));

        double result = dailyExpensesService.calculateDailyExpensesForSpecificDate(afterDate);

        assertTrue(result != 0.00);
    }

    @Test
    void calculateDailyExpensesForSpecificDate_doesItCalculateCorrectlyAndFindExistingExpense_okay() {

        when(busService.getDailyCostOfAllBuses()).thenReturn(dailyCostOfBuses);

        when(driverService.calculateDailyWage()).thenReturn(dailyWagesOfDrivers);

        when(tripRepository.findByDepartureDate(afterDate)).thenReturn(testListOfTrips);

        when(dailyRevenueService.getDailyRevenueIncomeByDate(afterDate)).thenReturn(revenueForSpecificDate);

        when(dailyExpensesRepository.findAll()).thenReturn(testListOfDailyExpenses);

        when(dailyExpensesRepository.findByExpenseDate(afterDate)).thenReturn(Optional.ofNullable(dailyExpense));

        double result = dailyExpensesService.calculateDailyExpensesForSpecificDate(afterDate);

        assertEquals(expectedExpenseResult, result);
    }

    @Test
    void calculateDailyExpensesForSpecificDate_doesItCalculateCorrectlyAndDoesNotFindExistingExpense_okay() {

        LocalDate dateOfNotExistingDailyExpense = LocalDate.of(1999, 5, 5);

        testListOfDailyExpenses.get(0).setExpenseDate(dateOfNotExistingDailyExpense);

        when(busService.getDailyCostOfAllBuses()).thenReturn(dailyCostOfBuses);

        when(driverService.calculateDailyWage()).thenReturn(dailyWagesOfDrivers);

        when(tripRepository.findByDepartureDate(afterDate)).thenReturn(testListOfTrips);

        when(dailyRevenueService.getDailyRevenueIncomeByDate(afterDate)).thenReturn(revenueForSpecificDate);

        when(dailyExpensesRepository.findAll()).thenReturn(testListOfDailyExpenses);

        double result = dailyExpensesService.calculateDailyExpensesForSpecificDate(afterDate);

        assertEquals(expectedExpenseResult, result);
    }
}