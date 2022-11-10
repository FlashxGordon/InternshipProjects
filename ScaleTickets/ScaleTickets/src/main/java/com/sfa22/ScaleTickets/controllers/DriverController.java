package com.sfa22.ScaleTickets.controllers;


import com.sfa22.ScaleTickets.dtos.DriverDto;
import com.sfa22.ScaleTickets.services.interfaces.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }


    @GetMapping("/drivers")
    public ResponseEntity<List<DriverDto>> getAllDrivers() throws NoSuchElementException {
        List<DriverDto> drivers = driverService.getAllDrivers();
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    @GetMapping("/drivers/{id}")
    public ResponseEntity<DriverDto> getDriverById(@PathVariable int id) {
        return ResponseEntity.ok(driverService.getDriverById(id).get());
    }


    @GetMapping(value = "/drivers", params = {"firstName"})
    public ResponseEntity<List<DriverDto>> getDriverByFirstName(@RequestParam(name = "firstName")
                                                                @NotBlank(message = "First Name cannot be Blank")
                                                                @NotEmpty(message = "First Name cannot be Empty")
                                                                @NotNull(message = "First Name cannot be null")
                                                                @Size(max = 30, message = "Maximum name length is 30 characters")
                                                                String firstName) throws NoSuchElementException {

        List<DriverDto> drivers = driverService.getDriverByFirstName(firstName);
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }


    @GetMapping(value = "/drivers", params = {"lastName"})
    public ResponseEntity<List<DriverDto>> getDriverByLastName(@RequestParam(name = "lastName")
                                                               @NotBlank(message = "Last Name cannot be Blank")
                                                               @NotEmpty(message = "Last Name cannot be Empty")
                                                               @NotNull(message = "Last Name cannot be null")
                                                               @Size(max = 30, message = "Maximum name length is 30 characters")
                                                               String lastName)
            throws NoSuchElementException {
        List<DriverDto> drivers = driverService.getDriverByLastName(lastName);
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }


    @GetMapping(value = "/drivers", params = {"dailyWageLessThan"})
    public ResponseEntity<List<DriverDto>> getDriverByDailyWageLessThan(@RequestParam(name = "dailyWageLessThan")
                                                                        @PositiveOrZero(message = "Daily wage must be positive or equals to zero")
                                                                        double dailyWage)
            throws NoSuchElementException {
        List<DriverDto> drivers = driverService.getDriverByDailyWageLessThan(dailyWage);
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }


    @GetMapping(value = "/drivers", params = {"dailyWageEquals"})
    public ResponseEntity<List<DriverDto>> getDriverByDailyWageEquals(@RequestParam(name = "dailyWageEquals")
                                                                      @PositiveOrZero(message = "Daily wage must be positive or equals to zero")
                                                                      double dailyWage)
            throws NoSuchElementException {
        List<DriverDto> drivers = driverService.getDriverByDailyWageEquals(dailyWage);
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }


    @GetMapping(value = "/drivers", params = {"dailyWageGreaterThan"})
    public ResponseEntity<List<DriverDto>> getDriverByDailyWageGreaterThan(@RequestParam(name = "dailyWageGreaterThan")
                                                                           @PositiveOrZero(message = "Daily wage must be positive or equals to zero")
                                                                           double dailyWage)
            throws NoSuchElementException {
        List<DriverDto> drivers = driverService.getDriverByDailyWageGreaterThan(dailyWage);
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }


    @GetMapping(value = "/drivers", params = {"phoneNumber"})
    public ResponseEntity<DriverDto> getDriverByPhoneNumber(@RequestParam(name = "phoneNumber")
                                                            @NotBlank(message = "Phone Number cannot be Blank")
                                                            @NotEmpty(message = "Phone Number cannot be Empty")
                                                            @NotNull(message = "Phone Number cannot be null")
                                                            @Size(max = 13, message = "Phone number must not be longer than 13 characters")
                                                            String phoneNumber)
            throws NoSuchElementException {
        DriverDto driver = driverService.getDriverByPhoneNumber(phoneNumber);
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }


    @PostMapping(value = "/drivers")
    public ResponseEntity<Void> addNewDriver(@Valid @RequestBody DriverDto driverDto) {

        int newDriverID = driverService.addNewDriver(driverDto);

        return ResponseEntity.created(URI.create("api/v1/drivers/" + newDriverID)).build();
    }


    @DeleteMapping(value = "/drivers", params = {"phoneNumber"})
    public void deleteDriverByPhoneNumber(@RequestParam(name = "phoneNumber")
                                          @NotBlank(message = "Phone Number cannot be Blank")
                                          @NotEmpty(message = "Phone Number cannot be Empty")
                                          @NotNull(message = "Phone Number cannot be null")
                                          @Size(max = 13, message = "Phone number must not be longer than 13 characters")
                                          String phoneNumber) {

        driverService.deleteDriverByPhoneNumber(phoneNumber);
    }
}