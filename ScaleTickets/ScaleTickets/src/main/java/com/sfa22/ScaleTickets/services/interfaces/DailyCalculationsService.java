package com.sfa22.ScaleTickets.services.interfaces;

import java.time.LocalDate;

public interface DailyCalculationsService {
  boolean calculateDailyRevenueExpensesAndProfit(LocalDate date);
}
