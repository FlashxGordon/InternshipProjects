package com.sfa22.ScaleTickets.services.implementations;

import com.sfa22.ScaleTickets.services.interfaces.DailyCalculationsService;
import com.sfa22.ScaleTickets.services.interfaces.DailyExpensesService;
import com.sfa22.ScaleTickets.services.interfaces.DailyGrossProfitService;
import com.sfa22.ScaleTickets.services.interfaces.DailyRevenueService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
public class DailyCalculationsServiceImpl implements DailyCalculationsService {

    private final DailyRevenueService dailyRevenueService;
    private final DailyExpensesService dailyExpensesService;
    private final DailyGrossProfitService dailyGrossProfitService;

    public DailyCalculationsServiceImpl(DailyRevenueService dailyRevenueService,
                                        DailyExpensesService dailyExpensesService,
                                        DailyGrossProfitService dailyGrossProfitService) {
        this.dailyRevenueService = dailyRevenueService;
        this.dailyExpensesService = dailyExpensesService;
        this.dailyGrossProfitService = dailyGrossProfitService;
    }

    @Transactional
    @Override
    public boolean calculateDailyRevenueExpensesAndProfit(LocalDate date) {
        dailyRevenueService.updateDailyRevenue(date);
        dailyExpensesService.calculateDailyExpensesForSpecificDate(date);
        dailyGrossProfitService.updateDailyGrossProfit(date);
        return true;
    }
}
