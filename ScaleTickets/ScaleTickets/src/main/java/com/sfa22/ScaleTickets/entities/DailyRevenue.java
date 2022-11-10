package com.sfa22.ScaleTickets.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "daily_revenue")
public class DailyRevenue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "revenue_id", unique = true, updatable = false, insertable = false, nullable = false)
    private int revenueId;
    @Column(name = "daily_income")
    private double dailyIncome;

    @Column(name = "income_date")
    private LocalDate incomeDate;

    public DailyRevenue() {
    }

    public DailyRevenue(double dailyIncome, LocalDate incomeDate) {
        this.dailyIncome = dailyIncome;
        this.incomeDate = incomeDate;
    }

    public DailyRevenue(int revenueId, double dailyIncome, LocalDate incomeDate) {
        this.revenueId = revenueId;
        this.dailyIncome = dailyIncome;
        this.incomeDate = incomeDate;
    }

    public int getRevenueId() {
        return revenueId;
    }

    public void setRevenueId(int revenueId) {
        this.revenueId = revenueId;
    }

    public double getDailyIncome() {
        return dailyIncome;
    }

    public void setDailyIncome(double dailyIncome) {
        this.dailyIncome = dailyIncome;
    }

    public LocalDate getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(LocalDate incomeDate) {
        this.incomeDate = incomeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DailyRevenue)) return false;
        DailyRevenue that = (DailyRevenue) o;
        return revenueId == that.revenueId
                && Double.compare(that.dailyIncome, dailyIncome) == 0
                && incomeDate.equals(that.incomeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(revenueId, dailyIncome, incomeDate);
    }
}








