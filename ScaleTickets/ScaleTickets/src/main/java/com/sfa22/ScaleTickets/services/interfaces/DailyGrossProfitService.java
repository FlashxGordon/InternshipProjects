package com.sfa22.ScaleTickets.services.interfaces;

import com.sfa22.ScaleTickets.dtos.DailyGrossProfitDto;

import java.time.LocalDate;
import java.util.List;

public interface DailyGrossProfitService {
    public List<DailyGrossProfitDto> getAllDailyGrossProfit();

    public DailyGrossProfitDto getByProfitDate(LocalDate Date);

    public List<DailyGrossProfitDto> getByProfitDateGreaterThan(LocalDate profitDate);

    public List<DailyGrossProfitDto> getByProfitDateBefore(LocalDate profitDate);

    public List<DailyGrossProfitDto> getByProfitDateBetween(LocalDate startDate, LocalDate endDate);

    public double calculateDailyGrossProfitForSpecificDate(LocalDate profitDate);

    public double calculateDailyGrossProfitInDateRange(LocalDate startDate, LocalDate endDate);

    public DailyGrossProfitDto updateDailyGrossProfit(LocalDate targetDate);
}
