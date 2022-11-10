package com.sfa22.ScaleTickets.services.implementations;

import com.sfa22.ScaleTickets.dtos.DataComparisonResultDto;
import com.sfa22.ScaleTickets.exceptions.InvalidUserInputException;
import com.sfa22.ScaleTickets.mappers.DataComparisonMapper;
import com.sfa22.ScaleTickets.services.interfaces.DailyExpensesService;
import com.sfa22.ScaleTickets.services.interfaces.DailyGrossProfitService;
import com.sfa22.ScaleTickets.services.interfaces.DailyRevenueService;
import com.sfa22.ScaleTickets.services.interfaces.DataComparisonService;
import com.sfa22.ScaleTickets.validations.DataComparisonValidation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DataComparisonServiceImpl implements DataComparisonService {

    private final DailyRevenueService dailyRevenueService;
    private final DailyExpensesService dailyExpensesService;
    private final DailyGrossProfitService dailyGrossProfitService;
    private final DataComparisonMapper comparisonMapper;
    private final DataComparisonValidation dataComparisonValidation;


    public DataComparisonServiceImpl(DailyRevenueService dailyRevenueService,
                                     DailyExpensesService dailyExpensesService,
                                     DailyGrossProfitService dailyGrossProfitService,
                                     DataComparisonMapper comparisonMapper, DataComparisonValidation dataComparisonValidation) {

        this.dailyRevenueService = dailyRevenueService;
        this.dailyExpensesService = dailyExpensesService;
        this.dailyGrossProfitService = dailyGrossProfitService;
        this.comparisonMapper = comparisonMapper;
        this.dataComparisonValidation = dataComparisonValidation;
    }

    /**
     * Method compareDataInDateRange takes in 2 dates for the current date range and 2 dates for the past
     * date range. Using those inputs and the methods provided by dailyRevenueService,
     * dailyExpensesService, and the dailyGrossProfitService the method calculates the
     * percentage difference between the sums of revenues, expenses and profits generated during the current and the past date range.
     * <p>
     * The purpose of this method is to provide the user with information concerning by how many percentage points
     * revenues, expenses and profits have increased and decreased compared to a selected date range from the past.
     *
     * @param startDateNew, endDateNew, startDateOld, endDateOld
     * @return DataComparisonResultDto - containing the date ranges and a double of percentageDifference for all 3 categories
     */

    public DataComparisonResultDto compareDataInDateRange(LocalDate startDateNew,
                                                          LocalDate endDateNew,
                                                          LocalDate startDateOld,
                                                          LocalDate endDateOld) {

        if (dataComparisonValidation.isNewDateRangeInputInCorrectOrder(startDateNew, endDateNew)) {

            throw new InvalidUserInputException("New date range input issue: The end date cannot be before the start date");
        }

        if (dataComparisonValidation.isOldDateRangeInputInCorrectOrder(startDateOld, endDateOld)) {

            throw new InvalidUserInputException("Old date range input issue: The end date cannot be before the start date");
        }

        if (dataComparisonValidation.doDateRangesInterfere(startDateNew, endDateOld)) {

            throw new InvalidUserInputException("New and old date ranges are interfering.");
        }

        double percentageDifferenceRevenues = Math.round(getRevenuePercentageDifference(startDateNew,
                endDateNew,
                startDateOld,
                endDateOld));

        double percentageDifferenceExpenses = Math.round(getExpensesPercentageDifference(startDateNew,
                endDateNew,
                startDateOld,
                endDateOld));

        double percentageDifferenceProfits = Math.round(getProfitPercentageDifference(startDateNew,
                endDateNew,
                startDateOld,
                endDateOld));

        return comparisonMapper.mapToResultDto(startDateNew,
                endDateNew, startDateOld, endDateOld,
                percentageDifferenceRevenues, percentageDifferenceExpenses, percentageDifferenceProfits);
    }

    private double getRevenuePercentageDifference(LocalDate startDateNew,
                                                  LocalDate endDateNew,
                                                  LocalDate startDateOld,
                                                  LocalDate endDateOld) {
        double oldRangeValue = getRevenueInRange(startDateOld, endDateOld);
        double newRangeValue = getRevenueInRange(startDateNew, endDateNew);

        return ((newRangeValue - oldRangeValue) / Math.abs(oldRangeValue)) * 100;

    }

    private double getRevenueInRange(LocalDate startDate, LocalDate endDate) {

        if (dailyRevenueService.
                getTotalDailyRevenueInDateRange(startDate, endDate) <= 0) {
            throw
                    new ArithmeticException("Negative or zero values not subject to calculation.");
        }
        return Math.round(dailyRevenueService.
                getTotalDailyRevenueInDateRange(startDate, endDate));
    }


    private double getExpensesPercentageDifference(LocalDate startDateNew,
                                                   LocalDate endDateNew,
                                                   LocalDate startDateOld,
                                                   LocalDate endDateOld) {
        double oldRangeValue = getExpensesInRange(startDateOld, endDateOld);
        double newRangeValue = getExpensesInRange(startDateNew, endDateNew);

        return ((newRangeValue - oldRangeValue) / Math.abs(oldRangeValue)) * 100;
    }


    private double getExpensesInRange(LocalDate startDate, LocalDate endDate) {

        if (dailyExpensesService.calculateDailyExpensesBetween(startDate, endDate) <= 0) {
            throw
                    new ArithmeticException("Negative or zero values not subject to calculation.");
        }
        return Math.round(dailyExpensesService.
                calculateDailyExpensesBetween(startDate, endDate));
    }

    private double getProfitPercentageDifference(LocalDate startDateNew,
                                                 LocalDate endDateNew,
                                                 LocalDate startDateOld,
                                                 LocalDate endDateOld) {
        double oldRangeValue = getProfitInRange(startDateOld, endDateOld);
        double newRangeValue = getProfitInRange(startDateNew, endDateNew);

        return ((newRangeValue - oldRangeValue) / Math.abs(oldRangeValue)) * 100;
    }


    private double getProfitInRange(LocalDate startDate, LocalDate endDate) {
        if (dailyGrossProfitService.calculateDailyGrossProfitInDateRange(startDate, endDate) <= 0) {
            throw
                    new ArithmeticException("Negative or zero values not subject to calculation.");
        }
        return Math.round(dailyGrossProfitService.
                calculateDailyGrossProfitInDateRange(startDate, endDate));
    }

}
