package com.sfa22.ScaleTickets.validations;

import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class DailyRevenueValidation {

    public boolean isDateRangeInputInCorrectOrder(LocalDate dateStart, LocalDate dateEnd) {

        return dateEnd.isAfter(dateStart);
    }

}
