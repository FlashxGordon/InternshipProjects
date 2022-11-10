package com.sfa22.ScaleTickets.mappers;

import com.sfa22.ScaleTickets.dtos.DataComparisonResultDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataComparisonMapper {

    public DataComparisonResultDto mapToResultDto(LocalDate startDateCurrent,
                                                  LocalDate endDateCurrent,
                                                  LocalDate startDatePast,
                                                  LocalDate endDatePast,
                                                  double percentageDifferenceRevenues,
                                                  double percentageDifferenceExpenses,
                                                  double percentageDifferenceProfits) {

        return new DataComparisonResultDto(startDateCurrent,
                endDateCurrent,
                startDatePast,
                endDatePast,
                percentageDifferenceRevenues,
                percentageDifferenceExpenses,
                percentageDifferenceProfits);
    }
}
