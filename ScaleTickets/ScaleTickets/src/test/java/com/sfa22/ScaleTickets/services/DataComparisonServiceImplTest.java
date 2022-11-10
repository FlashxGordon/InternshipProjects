package com.sfa22.ScaleTickets.services;

import com.sfa22.ScaleTickets.dtos.DataComparisonResultDto;
import com.sfa22.ScaleTickets.exceptions.InvalidUserInputException;
import com.sfa22.ScaleTickets.mappers.DataComparisonMapper;
import com.sfa22.ScaleTickets.services.implementations.DailyExpensesServiceImpl;
import com.sfa22.ScaleTickets.services.implementations.DailyGrossProfitServiceImpl;
import com.sfa22.ScaleTickets.services.implementations.DailyRevenueServiceImpl;
import com.sfa22.ScaleTickets.services.implementations.DataComparisonServiceImpl;
import com.sfa22.ScaleTickets.validations.DataComparisonValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataComparisonServiceImplTest {

    @Spy
    DataComparisonValidation dataComparisonValidation;

    @Spy
    DataComparisonMapper mapper;

    @Mock
    DailyRevenueServiceImpl dailyRevenueService;

    @Mock
    DailyExpensesServiceImpl dailyExpensesService;

    @Mock
    DailyGrossProfitServiceImpl dailyGrossProfitService;

    @InjectMocks
    DataComparisonServiceImpl dataComparisonService;

    LocalDate START_DATE_NEW, END_DATE_NEW, START_DATE_OLD, END_DATE_OLD;

    DataComparisonResultDto RESULT_DTO;

    double PERCENT_REVENUES, PERCENT_EXPENSES, PERCENT_PROFITS;

    double SUM_REVENUES_NEW, SUM_EXPENSES_NEW, SUM_PROFITS_NEW;

    double SUM_REVENUES_OLD, SUM_EXPENSES_OLD, SUM_PROFITS_OLD;

    double SUM_PROFITS_NEGATIVE_NEW, SUM_EXPENSES_NEGATIVE_NEW, SUM_REVENUES_NEGATIVE_OR_ZERO_NEW;

    double SUM_PROFITS_NEGATIVE_OLD, SUM_EXPENSES_NEGATIVE_OLD, SUM_REVENUES_NEGATIVE_OR_ZERO_OLD;

    @BeforeEach
    public void setUp() {

        START_DATE_NEW = LocalDate.of(2022, 9, 1);
        END_DATE_NEW = LocalDate.of(2022, 9, 30);

        START_DATE_OLD = LocalDate.of(2022, 8, 1);
        END_DATE_OLD = LocalDate.of(2022, 8, 31);

        SUM_REVENUES_NEW = 1000;
        SUM_EXPENSES_NEW = 500;
        SUM_PROFITS_NEW = 500;

        SUM_REVENUES_NEGATIVE_OR_ZERO_NEW = 0;
        SUM_EXPENSES_NEGATIVE_NEW = -100;
        SUM_PROFITS_NEGATIVE_NEW = -100;

        SUM_REVENUES_NEGATIVE_OR_ZERO_OLD = 0;
        SUM_EXPENSES_NEGATIVE_OLD = -100;
        SUM_PROFITS_NEGATIVE_OLD = -100;

        SUM_REVENUES_OLD = 500;
        SUM_EXPENSES_OLD = 250;
        SUM_PROFITS_OLD = 250;

        PERCENT_REVENUES = 100;
        PERCENT_EXPENSES = 100;
        PERCENT_PROFITS = 100;

        RESULT_DTO = new DataComparisonResultDto(START_DATE_NEW,
                END_DATE_NEW,
                START_DATE_OLD,
                END_DATE_OLD,
                PERCENT_REVENUES,
                PERCENT_EXPENSES,
                PERCENT_PROFITS);


    }

    @Test
    void compareDataInDateRange_doesItThrowNewDateRangeException_oK() {


        Throwable runtimeException = assertThrows(InvalidUserInputException.class, () ->
                        dataComparisonService.compareDataInDateRange(END_DATE_NEW, START_DATE_NEW, START_DATE_OLD, END_DATE_OLD),
                "New date range input issue: The end date cannot be before the start date");

        assertTrue(runtimeException.getMessage().contains("New date range input issue:"));

    }


    @Test
    void compareDataInDateRange_doesItThrowDateRangeInterferingException_oK() {


        Throwable runtimeException = assertThrows(InvalidUserInputException.class, () ->
                        dataComparisonService.compareDataInDateRange(END_DATE_OLD, END_DATE_NEW, START_DATE_OLD, START_DATE_NEW),
                "New and old date ranges are interfering.");

        assertTrue(runtimeException.getMessage().contains("New and old date ranges are interfering."));

    }

    @Test
    void compareDataInDateRange_doesItThrowNewOldRangeException_oK() {


        Throwable runtimeException = assertThrows(InvalidUserInputException.class, () ->
                        dataComparisonService.compareDataInDateRange(START_DATE_NEW, END_DATE_NEW, END_DATE_OLD, START_DATE_OLD),
                "Old date range input issue: The end date cannot be before the start date");

        assertTrue(runtimeException.getMessage().contains("Old date range input issue:"));

    }

    @Test
    void compareDataInDateRange_Comparison_doesItThrowNegativeValueMessage_oK() {

        Throwable runtimeException = assertThrows(ArithmeticException.class, () ->
                        dataComparisonService.compareDataInDateRange(START_DATE_NEW, END_DATE_NEW, START_DATE_OLD, END_DATE_OLD),
                "Negative or zero values not subject to calculation.");

        assertTrue(runtimeException.getMessage().contains("Negative or zero"));

    }

    @Test
    void compareDataInDateRange_doesItReturn_ok() {
        when(dailyExpensesService.calculateDailyExpensesBetween(START_DATE_NEW, END_DATE_NEW)).thenReturn(SUM_EXPENSES_NEW);
        when(dailyRevenueService.getTotalDailyRevenueInDateRange(START_DATE_NEW, END_DATE_NEW)).thenReturn(SUM_REVENUES_NEW);
        when(dailyGrossProfitService.calculateDailyGrossProfitInDateRange(START_DATE_NEW, END_DATE_NEW)).thenReturn(SUM_PROFITS_NEW);
        when(dailyExpensesService.calculateDailyExpensesBetween(START_DATE_OLD, END_DATE_OLD)).thenReturn(SUM_EXPENSES_OLD);
        when(dailyRevenueService.getTotalDailyRevenueInDateRange(START_DATE_OLD, END_DATE_OLD)).thenReturn(SUM_REVENUES_OLD);
        when(dailyGrossProfitService.calculateDailyGrossProfitInDateRange(START_DATE_OLD, END_DATE_OLD)).thenReturn(SUM_PROFITS_OLD);

        dataComparisonService.compareDataInDateRange(START_DATE_NEW, END_DATE_NEW, START_DATE_OLD, END_DATE_OLD);

        assertNotNull(RESULT_DTO);
        assertEquals(PERCENT_PROFITS, RESULT_DTO.getDifferenceInPercentForRevenues());
    }

    @Test
    void compareDataInDateRange_doesItReturnExpectedValue_ok() {
        when(dailyExpensesService.calculateDailyExpensesBetween(START_DATE_NEW, END_DATE_NEW)).thenReturn(SUM_EXPENSES_NEW);
        when(dailyRevenueService.getTotalDailyRevenueInDateRange(START_DATE_NEW, END_DATE_NEW)).thenReturn(SUM_REVENUES_NEW);
        when(dailyGrossProfitService.calculateDailyGrossProfitInDateRange(START_DATE_NEW, END_DATE_NEW)).thenReturn(SUM_PROFITS_NEW);
        when(dailyExpensesService.calculateDailyExpensesBetween(START_DATE_OLD, END_DATE_OLD)).thenReturn(SUM_EXPENSES_OLD);
        when(dailyRevenueService.getTotalDailyRevenueInDateRange(START_DATE_OLD, END_DATE_OLD)).thenReturn(SUM_REVENUES_OLD);
        when(dailyGrossProfitService.calculateDailyGrossProfitInDateRange(START_DATE_OLD, END_DATE_OLD)).thenReturn(SUM_PROFITS_OLD);

        dataComparisonService.compareDataInDateRange(START_DATE_NEW, END_DATE_NEW, START_DATE_OLD, END_DATE_OLD);

        assertEquals(PERCENT_REVENUES, RESULT_DTO.getDifferenceInPercentForRevenues());
        assertEquals(PERCENT_PROFITS, RESULT_DTO.getDifferenceInPercentForProfits());
        assertEquals(PERCENT_EXPENSES, RESULT_DTO.getDifferenceInPercentForExpenses());
    }

}

