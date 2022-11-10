package com.sfa22.ScaleTickets.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "daily_expenses")
public class DailyExpenses {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id", unique = true, updatable = false, insertable = false, nullable = false)
    private int expenseId;
    @Column(name = "fixed_cost")
    private double dailyFixedCost;

    @Column(name = "fuel_cost")
    private double dailyFuelCost;

    @Column(name = "software_cost")
    private double dailySoftwareCost;

    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;

    public DailyExpenses() {
    }

    public DailyExpenses(double dailyFixedCost, double dailyFuelCost, double dailySoftwareCost, LocalDate expenseDate) {
        this.dailyFixedCost = dailyFixedCost;
        this.dailyFuelCost = dailyFuelCost;
        this.dailySoftwareCost = dailySoftwareCost;
        this.expenseDate = expenseDate;
    }

    public DailyExpenses(int expenseId, double dailyFixedCost, double dailyFuelCost, double dailySoftwareCost, LocalDate expenseDate) {
        this.expenseId = expenseId;
        this.dailyFixedCost = dailyFixedCost;
        this.dailyFuelCost = dailyFuelCost;
        this.dailySoftwareCost = dailySoftwareCost;
        this.expenseDate = expenseDate;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public double getDailyFixedCost() {
        return dailyFixedCost;
    }

    public void setDailyFixedCost(double dailyFixedCost) {
        this.dailyFixedCost = dailyFixedCost;
    }

    public double getDailyFuelCost() {
        return dailyFuelCost;
    }

    public void setDailyFuelCost(double dailyFuelCost) {
        this.dailyFuelCost = dailyFuelCost;
    }

    public double getDailySoftwareCost() {
        return dailySoftwareCost;
    }

    public void setDailySoftwareCost(double dailySoftwareCost) {
        this.dailySoftwareCost = dailySoftwareCost;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DailyExpenses)) return false;
        DailyExpenses that = (DailyExpenses) o;
        return expenseId == that.expenseId
                && Double.compare(that.dailyFixedCost, dailyFixedCost) == 0
                && Double.compare(that.dailyFuelCost, dailyFuelCost) == 0
                && Double.compare(that.dailySoftwareCost, dailySoftwareCost) == 0
                && expenseDate.equals(that.expenseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseId, dailyFixedCost, dailyFuelCost, dailySoftwareCost, expenseDate);
    }
}
