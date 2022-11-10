package com.sfa22.ScaleTickets.schedulingtasks;

import com.sfa22.ScaleTickets.services.interfaces.DailyCalculationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DailyCalculationTask {
    private final DailyCalculationsService dailyCalculationsService;

    private final Logger log = LoggerFactory.getLogger(DailyCalculationTask.class);

    public DailyCalculationTask(DailyCalculationsService dailyCalculationsService) {
        this.dailyCalculationsService = dailyCalculationsService;
    }

    /**
     * Using @Scheduled the method refreshes the databases tables for Daily Revenue, Daily Expenses and Daily Profits
     * for a period of 60 days from today. The purpose of this is having up-to-date information as the data is altered
     * every time a ticket is purchased, a bus is added or a trip is created.
     */

    @Scheduled(cron = "${jobs.cronSchedule:-}")
    public void calculateDailyRevenueExpensesAndProfit() {

        LocalDate date = LocalDate.now();

        log.info("Running daily calculations for revenue, expenses and profit");

        dailyCalculationsService.calculateDailyRevenueExpensesAndProfit(date);

        for (int i = 0; i < 60; i++) {

            date = date.plusDays(1);
            dailyCalculationsService.calculateDailyRevenueExpensesAndProfit(date);
        }
    }
}
