package com.sfa22.ScaleTickets.services.interfaces;

import com.sfa22.ScaleTickets.dtos.DailyRevenueDto;

import java.time.LocalDate;

import java.util.List;


public interface DailyRevenueService {


    DailyRevenueDto updateDailyRevenue(LocalDate targetDate);

    List<DailyRevenueDto> getAllDailyRevenues();

    DailyRevenueDto getByRevenueId(Integer revenueId);

    DailyRevenueDto getDailyRevenueByDate(LocalDate incomeDate);

    Double getTotalDailyRevenueInDateRange(LocalDate startDate, LocalDate endDate);

    List<DailyRevenueDto> getListOfDailyRevenuesInDateRange(LocalDate startDate, LocalDate endDate);

    Double getDailyRevenueIncomeByDate(LocalDate incomeDate);


}
