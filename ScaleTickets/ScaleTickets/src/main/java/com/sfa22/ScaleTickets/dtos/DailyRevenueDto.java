package com.sfa22.ScaleTickets.dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DailyRevenueDto {

    private int revenueId;

    private double dailyIncome;

    private LocalDate incomeDate;

    public DailyRevenueDto(){
    }

    public DailyRevenueDto(double dailyIncome, LocalDate incomeDate) {
        this.dailyIncome = dailyIncome;
        this.incomeDate = incomeDate;
    }

    public DailyRevenueDto(int revenueId, double dailyIncome, LocalDate incomeDate) {
        this.revenueId = revenueId;
        this.dailyIncome = dailyIncome;
        this.incomeDate = incomeDate;
    }

}
