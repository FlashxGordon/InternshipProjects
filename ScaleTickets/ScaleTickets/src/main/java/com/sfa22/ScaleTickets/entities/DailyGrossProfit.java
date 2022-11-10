package com.sfa22.ScaleTickets.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "daily_gross_profit")
public class DailyGrossProfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gross_profit_id", unique = true, updatable = false, insertable = false, nullable = false)
    private int grossProfitId;

    @Column(name = "daily_gross_profit")
    private double dailyGrossProfit;

    @Column(name = "gross_profit_date")
    private LocalDate grossProfitDate;

    public DailyGrossProfit() {
    }

    public DailyGrossProfit(double dailyGrossProfit, LocalDate grossProfitDate) {
        this.dailyGrossProfit = dailyGrossProfit;
        this.grossProfitDate = grossProfitDate;
    }

    public DailyGrossProfit(int grossProfitId, double dailyGrossProfit, LocalDate grossProfitDate) {
        this.grossProfitId = grossProfitId;
        this.dailyGrossProfit = dailyGrossProfit;
        this.grossProfitDate = grossProfitDate;
    }

    public int getGrossProfitId() {
        return grossProfitId;
    }

    public void setGrossProfitId(int grossProfitId) {
        this.grossProfitId = grossProfitId;
    }

    public double getDailyGrossProfit() {
        return dailyGrossProfit;
    }

    public void setDailyGrossProfit(double dailyGrossProfit) {
        this.dailyGrossProfit = dailyGrossProfit;
    }

    public LocalDate getGrossProfitDate() {
        return grossProfitDate;
    }

    public void setGrossProfitDate(LocalDate grossProfitDate) {
        this.grossProfitDate = grossProfitDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DailyGrossProfit)) return false;
        DailyGrossProfit that = (DailyGrossProfit) o;
        return grossProfitId == that.grossProfitId
                && Double.compare(that.dailyGrossProfit, dailyGrossProfit) == 0
                && grossProfitDate.equals(that.grossProfitDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grossProfitId, dailyGrossProfit, grossProfitDate);
    }
}
