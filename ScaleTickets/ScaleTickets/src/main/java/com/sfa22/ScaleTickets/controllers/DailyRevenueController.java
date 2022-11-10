package com.sfa22.ScaleTickets.controllers;

import com.sfa22.ScaleTickets.dtos.DailyRevenueDto;
import com.sfa22.ScaleTickets.services.interfaces.DailyRevenueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class DailyRevenueController {

    private final DailyRevenueService dailyRevenueService;


    public DailyRevenueController(DailyRevenueService dailyRevenueService) {
        this.dailyRevenueService = dailyRevenueService;
    }

    @GetMapping("/revenue/{id}")
    public ResponseEntity<DailyRevenueDto> getRevenueById(@PathVariable int id) {

        return ResponseEntity.ok(dailyRevenueService.getByRevenueId(id));
    }

    @GetMapping("/revenue")
    public ResponseEntity<List<DailyRevenueDto>> getAllDailyRevenues() {

        return ResponseEntity.ok(dailyRevenueService.getAllDailyRevenues());
    }


    @GetMapping(value = "/revenue", params = {"byDate"})
    public ResponseEntity<DailyRevenueDto> getDailyRevenuesByDate(@RequestParam(name = "byDate") String returnDate) {

        return ResponseEntity
                .ok(dailyRevenueService.getDailyRevenueByDate(LocalDate.parse(returnDate)));
    }

    @GetMapping(value = "/revenue/income", params = {"byDate"})
    public ResponseEntity<Double> getDailyRevenueIncomeByDate(@RequestParam(name = "byDate") String returnDate) {

        return ResponseEntity
                .ok(dailyRevenueService.getDailyRevenueIncomeByDate(LocalDate.parse(returnDate)));
    }

    @GetMapping(value = "/revenue/total", params = {"startDate", "endDate"})
    public ResponseEntity<Double> getTotalRevenuesInDateRange(@RequestParam(name = "startDate") String startDate,
                                                              @RequestParam(name = "endDate") String endDate) {

        return ResponseEntity.ok(dailyRevenueService.getTotalDailyRevenueInDateRange
                (LocalDate.parse(startDate), LocalDate.parse(endDate)));
    }


    @GetMapping(value = "/revenue", params = {"startDate", "endDate"})
    public ResponseEntity<List<DailyRevenueDto>> getListOfRevenuesInDateRange(@RequestParam(name = "startDate") String startDate,
                                                                              @RequestParam(name = "endDate") String endDate) {

        return ResponseEntity.ok(dailyRevenueService.getListOfDailyRevenuesInDateRange
                (LocalDate.parse(startDate), LocalDate.parse(endDate)));
    }
}
