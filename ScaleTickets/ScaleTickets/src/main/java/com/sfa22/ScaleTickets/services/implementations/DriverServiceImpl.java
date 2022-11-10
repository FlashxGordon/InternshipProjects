package com.sfa22.ScaleTickets.services.implementations;


import com.sfa22.ScaleTickets.data.DriverRepository;
import com.sfa22.ScaleTickets.dtos.DriverDto;
import com.sfa22.ScaleTickets.entities.Driver;
import com.sfa22.ScaleTickets.exceptions.InvalidUserInputException;
import com.sfa22.ScaleTickets.exceptions.ResourceMissingException;
import com.sfa22.ScaleTickets.mappers.DriverMapper;
import com.sfa22.ScaleTickets.services.interfaces.DriverService;
import com.sfa22.ScaleTickets.validations.DriverValidation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    private final DriverMapper driverMapper;

    private final DriverValidation driverValidation;

    public DriverServiceImpl(DriverRepository driverRepository, DriverMapper driverMapper,
                             DriverValidation driverValidation) {

        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
        this.driverValidation = driverValidation;
    }

    @Override
    public List<DriverDto> getAllDrivers() {

        List<Driver> allDrivers = driverRepository.findAll();

        return driverMapper.ListOfDriverToDriverDto(allDrivers);
    }

    @Override
    public Optional<DriverDto> getDriverById(int id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceMissingException("Driver not found", "Driver"));

        return Optional.ofNullable(driverMapper.mapDriverToDriverDto(driver));
    }

    @Override
    public List<DriverDto> getDriverByFirstName(String firstName) {

        List<Driver> driversByFirstname = driverRepository.findByFirstName(firstName);

        return driverMapper.ListOfDriverToDriverDto(driversByFirstname);
    }

    @Override
    public List<DriverDto> getDriverByLastName(String lastName) {

        List<Driver> driversByLastname = driverRepository.findByLastName(lastName);

        return driverMapper.ListOfDriverToDriverDto(driversByLastname);
    }

    @Override
    public List<DriverDto> getDriverByFirstAndFLastName(String firstName, String lastName) {

        List<Driver> driversByFirstAndLastname = driverRepository.findByFirstNameAndLastName(firstName, lastName);

        return driverMapper.ListOfDriverToDriverDto(driversByFirstAndLastname);
    }

    @Override
    public DriverDto getDriverByPhoneNumber(String phoneNumber) {

        isPhoneNumberCorrect(phoneNumber);

        return driverMapper.mapDriverToDriverDto(driverRepository.findByPhoneNumber(phoneNumber).orElseThrow(() ->
                new ResourceMissingException("Driver not Found", "Driver")));
    }

    @Override
    public List<DriverDto> getDriverByDailyWageLessThan(double dailyWage) {

        List<Driver> driversByDailyWageLessThan = driverRepository.findByDailyWageLessThan(dailyWage);

        return driverMapper.ListOfDriverToDriverDto(driversByDailyWageLessThan);
    }

    @Override
    public List<DriverDto> getDriverByDailyWageEquals(double dailyWage) {

        List<Driver> driversByDailyWageEquals = driverRepository.findByDailyWageEquals(dailyWage);

        return driverMapper.ListOfDriverToDriverDto(driversByDailyWageEquals);
    }

    @Override
    public List<DriverDto> getDriverByDailyWageGreaterThan(double dailyWage) {

        List<Driver> driversByDailyWageGreaterThan = driverRepository.findByDailyWageGreaterThan(dailyWage);

        return driverMapper.ListOfDriverToDriverDto(driversByDailyWageGreaterThan);
    }


    @Override
    public int addNewDriver(DriverDto newDriverDto) {

        isPhoneNumberCorrect(newDriverDto.getPhoneNumber());

        Driver driverToAdd = driverMapper.mapDriverDtoToDriver(newDriverDto);

        Driver driver = driverRepository.save(driverToAdd);

        return driver.getDriverID();
    }


    @Override
    public void deleteDriverByPhoneNumber(String phoneNumber) {

        isPhoneNumberCorrect(phoneNumber);

        Optional<Driver> optionalDriver = driverRepository.findByPhoneNumber(phoneNumber);

        Driver driverToDelete = optionalDriver.orElseThrow(() ->
                new ResourceMissingException("Driver not Found", "Driver"));

        driverRepository.delete(driverToDelete);
    }

    @Override
    public double calculateDailyWage() {
        List<Driver> allDrivers = driverRepository.findAll();
        double sum = 0;
        for (Driver driver : allDrivers) {
            sum += driver.getDailyWage();
        }
        return sum;
    }

    private void isPhoneNumberCorrect(String phoneNumber) {

        if (!driverValidation.isDriverPhoneNumberValid(phoneNumber)) {

            throw new InvalidUserInputException("Incorrect Phone Number Format");
        }
    }
}