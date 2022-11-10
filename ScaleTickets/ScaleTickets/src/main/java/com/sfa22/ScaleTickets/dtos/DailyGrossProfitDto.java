package com.sfa22.ScaleTickets.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyGrossProfitDto {

    private int grossProfitId;

    private double dailyGrossProfit;

    private LocalDate grossProfitDate;

    public DailyGrossProfitDto() {
    }

    public DailyGrossProfitDto(int grossProfitId, double dailyGrossProfit, LocalDate grossProfitDate) {
        this.grossProfitId = grossProfitId;
        this.dailyGrossProfit = dailyGrossProfit;
        this.grossProfitDate = grossProfitDate;
    }

    public DailyGrossProfitDto(double dailyGrossProfit, LocalDate grossProfitDate) {
        this.dailyGrossProfit = dailyGrossProfit;
        this.grossProfitDate = grossProfitDate;
    }
}