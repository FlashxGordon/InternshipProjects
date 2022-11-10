package com.sfa22.ScaleTickets.controllers;

import com.sfa22.ScaleTickets.dtos.DataComparisonResultDto;
import com.sfa22.ScaleTickets.services.interfaces.DataComparisonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
public class DataComparisonController {

    private final DataComparisonService dataComparisonService;

    public DataComparisonController(DataComparisonService dataComparisonService) {

        this.dataComparisonService = dataComparisonService;
    }

    @GetMapping(value = "/comparison", params = {"startDateNew", "endDateNew", "startDateOld", "endDateOld"})
    public ResponseEntity<DataComparisonResultDto> calculateDailyExpensesForSpecificDate(@RequestParam(name = "startDateNew") LocalDate startDateCurrent,
                                                                                         @RequestParam(name = "endDateNew") LocalDate endDateCurrent,
                                                                                         @RequestParam(name = "startDateOld") LocalDate startDatePast,
                                                                                         @RequestParam(name = "endDateOld") LocalDate endDatePast) {

        return ResponseEntity.ok(dataComparisonService.compareDataInDateRange(startDateCurrent,
                endDateCurrent,
                startDatePast,
                endDatePast));
    }

}
