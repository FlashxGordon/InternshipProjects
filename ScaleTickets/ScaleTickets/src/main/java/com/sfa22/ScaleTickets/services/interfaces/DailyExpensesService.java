package com.sfa22.ScaleTickets.services.interfaces;

import com.sfa22.ScaleTickets.dtos.DailyExpensesDto;


import java.time.LocalDate;
import java.util.List;


public interface DailyExpensesService {

    DailyExpensesDto findByExpenseDate(LocalDate profitDate);

    List<DailyExpensesDto> getByExpenseDateGreaterThan(LocalDate profitDate);

    List<DailyExpensesDto> getByExpenseDateBefore(LocalDate profitDate);

    List<DailyExpensesDto> getByExpenseDateBetween(LocalDate startDate, LocalDate endDate);

    double calculateDailyExpensesForSpecificDate(LocalDate profitDate);

    double calculateDailyExpensesBetween(LocalDate startDate, LocalDate endDate);
}
