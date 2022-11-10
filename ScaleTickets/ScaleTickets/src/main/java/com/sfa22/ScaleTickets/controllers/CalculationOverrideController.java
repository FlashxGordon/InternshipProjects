package com.sfa22.ScaleTickets.controllers;

import com.sfa22.ScaleTickets.schedulingtasks.DailyCalculationTask;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class CalculationOverrideController {

    private final DailyCalculationTask dailyCalculationTask;

    public CalculationOverrideController(DailyCalculationTask dailyCalculationTask) {
        this.dailyCalculationTask = dailyCalculationTask;
    }


    @PutMapping(value = "/override")
    public ResponseEntity<Void> overrideCalculationTask() {

        dailyCalculationTask.calculateDailyRevenueExpensesAndProfit();

        return ResponseEntity.ok().build();
    }

}
