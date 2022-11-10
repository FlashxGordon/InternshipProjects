package com.sfa22.ScaleTickets.controllers;

import com.sfa22.ScaleTickets.dtos.DailyExpensesDto;
import com.sfa22.ScaleTickets.services.implementations.DailyExpensesServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
@Validated
public class DailyExpensesController {

    private final DailyExpensesServiceImpl dailyExpensesService;

    public DailyExpensesController(DailyExpensesServiceImpl dailyExpensesService) {
        this.dailyExpensesService = dailyExpensesService;
    }

    @GetMapping(value = "/expenses", params = {"byDate"})
    public ResponseEntity<DailyExpensesDto> findByExpenseDate(@RequestParam(name = "byDate") String profitDate)
            throws NoSuchElementException {

        return ResponseEntity
                .ok(dailyExpensesService.findByExpenseDate(LocalDate.parse(profitDate)));
    }

    @GetMapping(value = "/expenses", params = {"byDateGreaterThan"})
    public ResponseEntity<List<DailyExpensesDto>> getByExpenseDateGreaterThan(@RequestParam(name = "byDateGreaterThan")
                                                                              String expensesDate) throws NoSuchElementException {

        return ResponseEntity.ok(dailyExpensesService.getByExpenseDateGreaterThan(LocalDate.parse(expensesDate)));
    }

    @GetMapping(value = "/expenses", params = {"byDateBefore"})
    public ResponseEntity<List<DailyExpensesDto>> getByExpenseDateBefore(@RequestParam(name = "byDateBefore")
                                                                         String expensesDate) throws NoSuchElementException {

        return ResponseEntity.ok(dailyExpensesService.getByExpenseDateBefore(LocalDate.parse(expensesDate)));
    }

    @GetMapping(value = "/expenses", params = {"startDate", "endDate"})
    public ResponseEntity<List<DailyExpensesDto>> getByExpenseDateBetween(@RequestParam(name = "startDate") String startDate,
                                                                          @RequestParam(name = "endDate") String endDate)
            throws NoSuchElementException {

        return ResponseEntity.ok(dailyExpensesService.getByExpenseDateBetween
                (LocalDate.parse(startDate), LocalDate.parse(endDate)));
    }

    @GetMapping(value = "/expenses/total", params = {"startDate", "endDate"})
    public ResponseEntity<Double> calculateDailyExpensesBetween(@RequestParam(name = "startDate") String startDate,
                                                                @RequestParam(name = "endDate") String endDate)
            throws NoSuchElementException {

        return ResponseEntity.ok(dailyExpensesService.calculateDailyExpensesBetween
                (LocalDate.parse(startDate), LocalDate.parse(endDate)));
    }

    @GetMapping(value = "/expenses", params = {"expensesDate"})
    public ResponseEntity<Double> calculateDailyExpensesForSpecificDate(@RequestParam(name = "expensesDate") String expensesDate)
            throws NoSuchElementException {

        return ResponseEntity.ok(dailyExpensesService.calculateDailyExpensesForSpecificDate
                (LocalDate.parse(expensesDate)));
    }


}
