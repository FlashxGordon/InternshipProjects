package com.sfa22.ScaleTickets.mappers;

import com.sfa22.ScaleTickets.dtos.DailyExpensesDto;
import com.sfa22.ScaleTickets.entities.DailyExpenses;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DailyExpensesMapper {

    public DailyExpenses mapDailyExpensesDtoToDailyExpenses(DailyExpensesDto dailyExpensesDto) {

        return new DailyExpenses(dailyExpensesDto.getExpenseId(), dailyExpensesDto.getDailyFixedCost(),
                dailyExpensesDto.getDailyFuelCost(), dailyExpensesDto.getDailySoftwareCost(),
                dailyExpensesDto.getExpenseDate());
    }

    public DailyExpensesDto mapDailyExpensesToDailyExpensesDto(DailyExpenses dailyExpenses) {

        return new DailyExpensesDto(dailyExpenses.getExpenseId(), dailyExpenses.getDailyFixedCost(),
                dailyExpenses.getDailyFuelCost(), dailyExpenses.getDailySoftwareCost(),
                dailyExpenses.getExpenseDate());
    }

    public List<DailyExpensesDto> mapListOfDailyExpensesToDailyExpensesDto(List<DailyExpenses> listOfDailyExpenses) {

        return listOfDailyExpenses.stream().map(this::mapDailyExpensesToDailyExpensesDto).collect(Collectors.toList());
    }
}