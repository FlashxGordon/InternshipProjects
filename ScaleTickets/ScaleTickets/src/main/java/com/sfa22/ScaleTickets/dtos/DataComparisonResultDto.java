package com.sfa22.ScaleTickets.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DataComparisonResultDto {

    private LocalDate startDateNew;

    private LocalDate endDateNew;

    private LocalDate startDateOld;

    private LocalDate endDateOld;

    private double differenceInPercentForRevenues;

    private double differenceInPercentForExpenses;

    private double differenceInPercentForProfits;

    public DataComparisonResultDto() {
    }

    public DataComparisonResultDto(LocalDate startDateNew,
                                   LocalDate endDateNew,
                                   LocalDate startDateOld,
                                   LocalDate endDateOld,
                                   double differenceInPercentForRevenues,
                                   double differenceInPercentForExpenses,
                                   double differenceInPercentForProfits) {
        this.startDateNew = startDateNew;
        this.endDateNew = endDateNew;
        this.startDateOld = startDateOld;
        this.endDateOld = endDateOld;
        this.differenceInPercentForRevenues = differenceInPercentForRevenues;
        this.differenceInPercentForExpenses = differenceInPercentForExpenses;
        this.differenceInPercentForProfits = differenceInPercentForProfits;
    }
}
