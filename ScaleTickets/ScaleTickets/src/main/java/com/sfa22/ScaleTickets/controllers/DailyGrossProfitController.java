package com.sfa22.ScaleTickets.controllers;

import com.sfa22.ScaleTickets.dtos.DailyGrossProfitDto;
import com.sfa22.ScaleTickets.services.interfaces.DailyGrossProfitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DailyGrossProfitController {
    private final DailyGrossProfitService dailyGrossProfitService;

    public DailyGrossProfitController(DailyGrossProfitService dailyGrossProfitService) {
        this.dailyGrossProfitService = dailyGrossProfitService;
    }


    @GetMapping("/grossProfit")
    public ResponseEntity<List<DailyGrossProfitDto>> getAllDailyGrossProfits() {

        return ResponseEntity.ok(dailyGrossProfitService.getAllDailyGrossProfit());
    }

    @GetMapping(value = "/grossProfit", params = {"byDate"})
    public ResponseEntity<DailyGrossProfitDto> getDailyGrossProfitsByDate(@RequestParam(name = "byDate") LocalDate returnDate) {

        return ResponseEntity
                .ok(dailyGrossProfitService.getByProfitDate(returnDate));
    }

    @GetMapping(value = "/grossProfit/total", params = {"startDate", "endDate"})
    public ResponseEntity<Double> getTotalGrossProfitInDateRange(
            @RequestParam(name = "startDate") LocalDate startDate,
            @RequestParam(name = "endDate") LocalDate endDate) {

        return ResponseEntity.ok(dailyGrossProfitService.calculateDailyGrossProfitInDateRange(startDate, endDate));
    }

    @GetMapping(value = "/grossProfit", params = {"startDate", "endDate"})
    public ResponseEntity<List<DailyGrossProfitDto>> getListOfGrossProfitsInDateRange(
            @RequestParam(name = "startDate") LocalDate startDate,
            @RequestParam(name = "endDate") LocalDate endDate) {

        return ResponseEntity.ok(dailyGrossProfitService.getByProfitDateBetween(startDate, endDate));
    }
}
