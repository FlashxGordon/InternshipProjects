package com.sfa22.ScaleTickets.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyExpensesDto {
    private int expenseId;
    private double dailyFixedCost;
    private double dailyFuelCost;
    private double dailySoftwareCost;
    private LocalDate expenseDate;

    public DailyExpensesDto() {
    }

    public DailyExpensesDto(double dailyFixedCost, double dailyFuelCost, double dailySoftwareCost, LocalDate expenseDate) {
        this.dailyFixedCost = dailyFixedCost;
        this.dailyFuelCost = dailyFuelCost;
        this.dailySoftwareCost = dailySoftwareCost;
        this.expenseDate = expenseDate;
    }

    public DailyExpensesDto(int expenseId, double dailyFixedCost, double dailyFuelCost, double dailySoftwareCost, LocalDate expenseDate) {
        this.expenseId = expenseId;
        this.dailyFixedCost = dailyFixedCost;
        this.dailyFuelCost = dailyFuelCost;
        this.dailySoftwareCost = dailySoftwareCost;
        this.expenseDate = expenseDate;
    }
}
