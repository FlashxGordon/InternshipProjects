package com.sfa22.ScaleTickets.services;


import com.sfa22.ScaleTickets.data.DailyProfitRepository;
import com.sfa22.ScaleTickets.data.DailyRevenueRepository;
import com.sfa22.ScaleTickets.dtos.DailyGrossProfitDto;
import com.sfa22.ScaleTickets.entities.DailyGrossProfit;
import com.sfa22.ScaleTickets.entities.DailyRevenue;
import com.sfa22.ScaleTickets.mappers.DailyGrossProfitMapper;
import com.sfa22.ScaleTickets.services.implementations.DailyExpensesServiceImpl;
import com.sfa22.ScaleTickets.services.implementations.DailyGrossProfitServiceImpl;
import com.sfa22.ScaleTickets.services.implementations.DailyRevenueServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DailyGrossProfitServiceImplTest {

    @Mock
    DailyProfitRepository dailyProfitRepository;

    @Mock
    DailyExpensesServiceImpl dailyExpensesService;

    @Mock
    DailyRevenueRepository dailyRevenueRepository;
    @Mock
    DailyRevenueServiceImpl dailyRevenueService;

    @Spy
    DailyGrossProfitMapper dailyGrossProfitMapper;


    @InjectMocks
    DailyGrossProfitServiceImpl dailyGrossProfitService;

    double dailyGrossProfit;

    double dailyIncome;

    double dailyExpenses;

    LocalDate grossProfitDate;

    LocalDate targetDate;

    LocalDate startDate;

    LocalDate endDate;

    DailyGrossProfit dailyProfit;

    DailyRevenue dailyRevenue;

    List<DailyGrossProfit> dailyGrossProfitList;
    List<DailyGrossProfitDto> dailyGrossProfitDtoList;

    List<Double> grossProfitSumList;

    @BeforeEach
    public void setUp() {
        targetDate = LocalDate.of(2022, 9, 6);
        startDate = LocalDate.of(2022, 9, 6);
        endDate = LocalDate.of(2022, 10, 6);
        grossProfitDate = LocalDate.of(2022, 9, 6);
        dailyIncome = 300.0;
        dailyExpenses = 200.0;
        dailyGrossProfit = 100.0;
        dailyRevenue = new DailyRevenue(300.0, startDate);
        dailyProfit = new DailyGrossProfit(dailyGrossProfit, grossProfitDate);
        dailyGrossProfitList = new ArrayList<>();
        dailyGrossProfitList.add(dailyProfit);
        grossProfitSumList = new ArrayList<>();
        grossProfitSumList.add(dailyGrossProfit);
        dailyGrossProfitDtoList = new ArrayList<>();

    }

    @Test
    void getByProfitDate_doesItReturnIt_okay() {
        when(dailyProfitRepository.findByGrossProfitDate(startDate)).thenReturn(Optional.ofNullable(dailyProfit));
        DailyGrossProfitDto dto = dailyGrossProfitMapper.mapDailyGrossProfitToDailyGrossProfitDto(dailyProfit);
        DailyGrossProfitDto profit = dailyGrossProfitService.getByProfitDate(startDate);
        assertEquals(dto, profit);
    }

    @Test
    void getAllDailyGrossProfit_doesItReturnAllGrossProfit_ok() {
        when(dailyProfitRepository.findAll()).thenReturn(dailyGrossProfitList);

        List<DailyGrossProfitDto> dailyGrossProfitDtoList = dailyGrossProfitService.getAllDailyGrossProfit();

        assertEquals(dailyGrossProfitDtoList.size(), dailyGrossProfitList.size());
    }

    @Test
    void getByProfitDateBetween_doesItReturnOkay_ok() {
        when(dailyProfitRepository.findByGrossProfitDateBetween(startDate, endDate)).thenReturn(dailyGrossProfitList);

        List<DailyGrossProfitDto> dtos = dailyGrossProfitMapper.mapListOfDailyGrossProfitToDailyGrossProfitDto(dailyGrossProfitList);
        List<DailyGrossProfitDto> resultList = dailyGrossProfitService.getByProfitDateBetween(startDate, endDate);
        assertEquals(dtos.size(), resultList.size());
    }

    @Test
    void calculateDailyGrossProfitInDateRange_sumsCorrectly_ok() {

        when(dailyRevenueService.getTotalDailyRevenueInDateRange(startDate, endDate)).thenReturn(dailyIncome);
        when(dailyExpensesService.calculateDailyExpensesBetween(startDate, endDate)).thenReturn(dailyExpenses);

        double result = dailyIncome - dailyExpenses;
        double res = dailyGrossProfitService.calculateDailyGrossProfitInDateRange(startDate, endDate);
        assertEquals(res, result);
    }


    @Test
    void getByProfitDateGreaterThan_returnsDailyGrossProfit_ok() {

        when(dailyProfitRepository.findByGrossProfitDateGreaterThan(startDate)).thenReturn(dailyGrossProfitList);
        List<DailyGrossProfitDto> dtos = dailyGrossProfitMapper.mapListOfDailyGrossProfitToDailyGrossProfitDto(dailyGrossProfitList);
        List<DailyGrossProfitDto> resultList = dailyGrossProfitService.getByProfitDateGreaterThan(startDate);
        assertEquals(dtos.size(), resultList.size());
    }

    @Test
    void calculateDailyGrossProfitForSpecificDate_doesItCalculateCorrectly_okay() {
        when(dailyRevenueRepository.findByIncomeDate(startDate)).thenReturn(Optional.ofNullable(dailyRevenue));
        when(dailyExpensesService.calculateDailyExpensesForSpecificDate(startDate)).thenReturn(dailyExpenses);

        double result = dailyGrossProfitService.calculateDailyGrossProfitForSpecificDate(startDate);
        double res = dailyRevenue.getDailyIncome() - dailyExpenses;
        assertEquals(res, result);
    }

    @Test
    void getByProfitDateBefore_returnsDailyGrossProfit_ok() {

        when(dailyProfitRepository.findByGrossProfitDateBefore(startDate)).thenReturn(dailyGrossProfitList);
        List<DailyGrossProfitDto> dtos = dailyGrossProfitMapper.mapListOfDailyGrossProfitToDailyGrossProfitDto(dailyGrossProfitList);
        List<DailyGrossProfitDto> resultList = dailyGrossProfitService.getByProfitDateBefore(startDate);
        assertEquals(dtos.size(), resultList.size());
    }

    @Test
    void updateDailyGrossProfit_doesItUpdateIfItHasProfitOnDate_okay() {
        when(dailyProfitRepository.findByGrossProfitDate(targetDate)).thenReturn(Optional.ofNullable(dailyProfit));
        when(dailyRevenueRepository.findByIncomeDate(targetDate)).thenReturn(Optional.ofNullable(dailyRevenue));
        when(dailyExpensesService.calculateDailyExpensesForSpecificDate(targetDate)).thenReturn(dailyExpenses);

        double amount = dailyRevenue.getDailyIncome() - dailyExpenses;
        DailyGrossProfit profit = new DailyGrossProfit(amount, targetDate);
        when(dailyProfitRepository.save(profit)).thenReturn(profit);

        DailyGrossProfitDto result = dailyGrossProfitService.updateDailyGrossProfit(targetDate);
        DailyGrossProfitDto dto = dailyGrossProfitMapper.mapDailyGrossProfitToDailyGrossProfitDto(profit);

        assertEquals(dto, result);
    }

    @Test
    void updateDailyGrossProfit_doesItUpdateIfItHasNoProfitOnDate_okay() {
        when(dailyProfitRepository.findByGrossProfitDate(targetDate)).thenReturn(Optional.ofNullable(null));
        when(dailyRevenueRepository.findByIncomeDate(targetDate)).thenReturn(Optional.ofNullable(dailyRevenue));
        when(dailyExpensesService.calculateDailyExpensesForSpecificDate(targetDate)).thenReturn(dailyExpenses);

        double amount = dailyRevenue.getDailyIncome() - dailyExpenses;
        DailyGrossProfit profit = new DailyGrossProfit(amount, targetDate);
        when(dailyProfitRepository.save(profit)).thenReturn(profit);
        DailyGrossProfitDto result = dailyGrossProfitService.updateDailyGrossProfit(targetDate);
        DailyGrossProfitDto dto = dailyGrossProfitMapper.mapDailyGrossProfitToDailyGrossProfitDto(profit);

        assertEquals(dto, result);
    }


}
