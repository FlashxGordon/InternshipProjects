package com.sfa22.ScaleTickets.validations;

import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class DataComparisonValidation {

    public boolean isNewDateRangeInputInCorrectOrder(LocalDate startDateNew, LocalDate endDateNew) {

        return endDateNew.isBefore(startDateNew);

    }


    public boolean isOldDateRangeInputInCorrectOrder(LocalDate startDateOld, LocalDate endDateOld) {

        return endDateOld.isBefore(startDateOld);

    }

    public boolean doDateRangesInterfere(LocalDate startDateNew,
                                         LocalDate endDateOld) {

        return (startDateNew.isBefore(endDateOld) || startDateNew.isEqual(endDateOld));

    }

}
