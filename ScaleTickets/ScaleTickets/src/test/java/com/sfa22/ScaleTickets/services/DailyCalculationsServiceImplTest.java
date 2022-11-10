package com.sfa22.ScaleTickets.services;

import com.sfa22.ScaleTickets.services.implementations.DailyCalculationsServiceImpl;
import com.sfa22.ScaleTickets.services.interfaces.DailyExpensesService;
import com.sfa22.ScaleTickets.services.interfaces.DailyGrossProfitService;
import com.sfa22.ScaleTickets.services.interfaces.DailyRevenueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DailyCalculationsServiceImplTest {
  @Mock
  private DailyRevenueService dailyRevenueService;
  @Mock
  private DailyExpensesService dailyExpensesService;
  @Mock
  private DailyGrossProfitService dailyGrossProfitService;
  @InjectMocks
  private DailyCalculationsServiceImpl dailyCalculationsService;

  LocalDate date = LocalDate.parse("2022-09-20");

  @Test
  public void calculateDailyRevenueExpensesAndProfit_date_callsAllWithSameDate() {
    dailyCalculationsService.calculateDailyRevenueExpensesAndProfit(date);

    verify(dailyRevenueService, times(1)).updateDailyRevenue(date);
    verify(dailyExpensesService, times(1)).calculateDailyExpensesForSpecificDate(date);
    verify(dailyGrossProfitService, times(1)).updateDailyGrossProfit(date);
  }
}

