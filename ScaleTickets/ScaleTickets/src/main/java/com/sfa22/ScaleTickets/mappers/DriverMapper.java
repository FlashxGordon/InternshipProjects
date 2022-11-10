package com.sfa22.ScaleTickets.mappers;

import com.sfa22.ScaleTickets.dtos.DriverDto;
import com.sfa22.ScaleTickets.entities.Driver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DriverMapper {
    public Driver mapDriverDtoToDriver(DriverDto driverDto) {

        return new Driver(driverDto.getDriverID(), driverDto.getFirstName(),
                driverDto.getLastName(), driverDto.getPhoneNumber(), driverDto.getDailyWage());
    }

    public DriverDto mapDriverToDriverDto(Driver driver) {

        return new DriverDto(driver.getDriverID(), driver.getFirstName(),
                driver.getLastName(), driver.getPhoneNumber(), driver.getDailyWage());

    }

    public List<DriverDto> ListOfDriverToDriverDto(List<Driver> listOfDrivers) {

        return listOfDrivers.stream().map(this::mapDriverToDriverDto).collect(Collectors.toList());
    }


}
