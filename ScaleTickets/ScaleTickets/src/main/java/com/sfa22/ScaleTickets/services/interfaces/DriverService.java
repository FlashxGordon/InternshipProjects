package com.sfa22.ScaleTickets.services.interfaces;


import com.sfa22.ScaleTickets.dtos.DriverDto;


import java.util.List;
import java.util.Optional;

public interface DriverService {


    List<DriverDto> getAllDrivers();

    Optional<DriverDto> getDriverById(int id);

    List<DriverDto> getDriverByFirstName(String firstName);

    List<DriverDto> getDriverByLastName(String lastName);

    List<DriverDto> getDriverByFirstAndFLastName(String firstName, String lastName);

    List<DriverDto> getDriverByDailyWageLessThan(double dailyWage);

    List<DriverDto> getDriverByDailyWageEquals(double dailyWage);

    List<DriverDto> getDriverByDailyWageGreaterThan(double dailyWage);

    DriverDto getDriverByPhoneNumber(String phoneNumber);

    int addNewDriver(DriverDto driverDto);

    public void deleteDriverByPhoneNumber(String phoneNumber);

    public double calculateDailyWage();
}
