package com.sfa22.ScaleTickets.services.interfaces;

import com.sfa22.ScaleTickets.dtos.DataComparisonResultDto;

import java.time.LocalDate;

public interface DataComparisonService {

    DataComparisonResultDto compareDataInDateRange(LocalDate startDateCurrent,
                                                   LocalDate endDateCurrent,
                                                   LocalDate startDatePast,
                                                   LocalDate endDatePast);


}
