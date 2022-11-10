package com.sfa22.ScaleTickets.controllers;

import com.sfa22.ScaleTickets.dtos.BusDto;
import com.sfa22.ScaleTickets.services.interfaces.BusService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Validated
public class BusController {

    private final BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping("/buses")
    public ResponseEntity<List<BusDto>> getAllBuses() {
        return ResponseEntity.ok(busService.getAllBusses());
    }

    @GetMapping(value = "/buses", params = {"plate"})
    public ResponseEntity<BusDto> getBusByPlate(@RequestParam(name = "plate")
                                                @NotBlank(message = "Plate cannot be Blank")
                                                @NotEmpty(message = "Plate cannot be Empty")
                                                @NotNull(message = "Plate cannot be null")
                                                @Size(min = 8, max = 8, message = "Bus plate must be 8 characters long")
                                                String busPlate) {

        return ResponseEntity.ok(busService.getBusByPlate(busPlate));
    }

    @GetMapping(value = "/buses", params = {"capacityLessThan"})
    public ResponseEntity<List<BusDto>> getBusesByCapacityLessThan(@RequestParam(name = "capacityLessThan")
                                                                   @Min(value = 10, message = "Capacity must be between 10 and 300")
                                                                   @Max(value = 300, message = "Capacity must be between 10 and 300")
                                                                   int busCapacity) {

        return ResponseEntity.ok(busService.getBusesByCapacityLessThan(busCapacity));
    }

    @GetMapping("/buses/{id}")
    public ResponseEntity<BusDto> getBusById(@PathVariable @PositiveOrZero int id) {
        return ResponseEntity.ok(busService.getBusById(id).get());
    }

    @GetMapping(value = "/buses", params = {"capacityGreaterThan"})
    public ResponseEntity<List<BusDto>> getBusesByCapacityGreaterThan(@RequestParam(name = "capacityGreaterThan")
                                                                      @Min(value = 10, message = "Capacity must be between 10 and 300")
                                                                      @Max(value = 300, message = "Capacity must be between 10 and 300")
                                                                      int busCapacity) {

        return ResponseEntity.ok(busService.getBusesByCapacityGreaterThan(busCapacity));
    }

    @GetMapping(value = "/buses", params = {"capacity"})
    public ResponseEntity<List<BusDto>> getBusesByCapacityEquals(@RequestParam(name = "capacity")
                                                                 @Min(value = 10, message = "Capacity must be between 10 and 300")
                                                                 @Max(value = 300, message = "Capacity must be between 10 and 300")
                                                                 int busCapacity) {

        return ResponseEntity.ok(busService.getBusesByCapacityEquals(busCapacity));
    }

    @GetMapping(value = "/buses", params = {"dailyCostLessThan"})
    public ResponseEntity<List<BusDto>> getBusesByDailyCostLessThan(@RequestParam(name = "dailyCostLessThan")
                                                                    @PositiveOrZero(message = "Daily bus cost me positive or zero")
                                                                    double busDailyCost) {

        return ResponseEntity.ok(busService.getByDailyCostLessThan(busDailyCost));
    }

    @GetMapping(value = "/buses", params = {"dailyCostGreaterThan"})
    public ResponseEntity<List<BusDto>> getBusesByDailyCostGreaterThan(@RequestParam(name = "dailyCostGreaterThan")
                                                                       @PositiveOrZero(message = "Daily bus cost me positive or zero")
                                                                       double busDailyCost) {

        return ResponseEntity.ok(busService.getByDailyCostGreaterThan(busDailyCost));
    }

    @GetMapping(value = "/buses", params = {"dailyCost"})
    public ResponseEntity<List<BusDto>> getBusesByDailyCostEquals(@RequestParam(name = "dailyCost")
                                                                  @PositiveOrZero(message = "Daily bus cost me positive or zero")
                                                                  double busDailyCost) {

        return ResponseEntity.ok(busService.getByDailyCostEquals(busDailyCost));
    }

    @GetMapping(value = "/buses", params = {"capacity", "dailyCost"})
    public ResponseEntity<List<BusDto>> getByBusCapacityEqualsAndDailyCostEquals(@RequestParam(name = "capacity")
                                                                                 @Min(value = 10, message = "Capacity must be between 10 and 300")
                                                                                 @Max(value = 300, message = "Capacity must be between 10 and 300")
                                                                                 int capacity,
                                                                                 @RequestParam(name = "dailyCost")
                                                                                 @PositiveOrZero(message = "Daily bus cost me positive or zero")
                                                                                 double busDailyCost) {

        return ResponseEntity.ok(busService.getByBusCapacityEqualsAndDailyCostEquals(capacity, busDailyCost));
    }

    @PatchMapping(value = "/buses", params = {"plate"})
    public void getBusByPlateAndUpdateFixCost(@RequestParam(name = "plate")
                                              @NotBlank(message = "Plate cannot be Blank")
                                              @NotEmpty(message = "Plate cannot be Empty")
                                              @NotNull(message = "Plate cannot be null")
                                              @Size(min = 8, max = 8, message = "Bus plate must be 8 characters long")
                                              String busPlate,
                                              @RequestBody
                                              BusDto busDto) {

        busService.getBusByPlateAndUpdateFixCost(busPlate, busDto.getDailyCost());
    }

    @DeleteMapping(value = "/buses", params = {"plate"})
    public void getBusServiceByPlateNumberAndDelete(@RequestParam(name = "plate")
                                                    @NotBlank(message = "Plate cannot be Blank")
                                                    @NotEmpty(message = "Plate cannot be Empty")
                                                    @NotNull(message = "Plate cannot be null")
                                                    @Size(min = 8, max = 8, message = "Bus plate must be 8 characters long")
                                                    String busPlate) {

        busService.getBusServiceByPlateNumberAndDelete(busPlate);
    }

    @GetMapping(value = "/buses/dailyCost")
    public ResponseEntity<Double> getDailyCostOfAllBuses() {

        return ResponseEntity.ok(busService.getDailyCostOfAllBuses());
    }

    @PostMapping(value = "/buses")
    public ResponseEntity<Void> addNewBus(@Valid @RequestBody BusDto busDto) {

        int newBusID = busService.addNewBus(busDto);

        return ResponseEntity.created(URI.create("api/v1/buses/" + newBusID)).build();
    }
}