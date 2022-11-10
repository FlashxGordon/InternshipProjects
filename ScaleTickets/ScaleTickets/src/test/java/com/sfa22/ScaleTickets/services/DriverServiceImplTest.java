package com.sfa22.ScaleTickets.services;


import com.sfa22.ScaleTickets.data.DriverRepository;
import com.sfa22.ScaleTickets.dtos.DriverDto;
import com.sfa22.ScaleTickets.entities.Driver;
import com.sfa22.ScaleTickets.exceptions.InvalidUserInputException;
import com.sfa22.ScaleTickets.exceptions.ResourceMissingException;
import com.sfa22.ScaleTickets.mappers.DriverMapper;
import com.sfa22.ScaleTickets.services.implementations.DriverServiceImpl;
import com.sfa22.ScaleTickets.validations.DriverValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DriverServiceImplTest {


    @Mock
    DriverRepository driverRepository;

    @Spy
    DriverMapper driverMapper;

    @Spy
    DriverValidation driverValidation;

    @InjectMocks
    DriverServiceImpl driverService;

    private int driverID;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String wrongPhoneNumber;

    private String newPhoneNumber;

    private double dailyWage;

    private List<Driver> testDrivers;

    Driver driver;

    Driver testDriver;

    @BeforeEach
    public void setUp() {

        driverID = 1;

        firstName = "Kristina";

        lastName = "Lajmanovska";

        phoneNumber = "+359891212145";

        wrongPhoneNumber = "+35414512345";

        newPhoneNumber = "+359871478415";

        dailyWage = 2000;

        driver = new Driver(driverID, firstName, lastName, phoneNumber, dailyWage);

        testDriver = new Driver(driverID, firstName, lastName, phoneNumber, dailyWage);

        testDrivers = new ArrayList<>();

        testDrivers.add(driver);

    }

    @Test
    void getAllDrivers_doesItReturnAllDrivers_okay() {

        when(driverRepository.findAll()).thenReturn(testDrivers);

        List<DriverDto> listOfDriverDto = driverService.getAllDrivers();

        assertEquals(listOfDriverDto.size(), testDrivers.size());
    }

    @Test
    void getById_doesItReturnDriverById_okay() {
        when(driverRepository.findById(1)).
                thenReturn(Optional.ofNullable(driver));
        Optional<DriverDto> foundDriver = driverService.getDriverById(1);
        Assertions.assertNotNull(foundDriver);

    }

    @Test
    void getDriverByFirstName_doesItReturnDriverByFirstName_okay() {

        when(driverRepository.findByFirstName(firstName)).thenReturn(testDrivers);

        List<DriverDto> listOfDriverDto = driverService.getDriverByFirstName(firstName);

        assertEquals(listOfDriverDto.size(), testDrivers.size());

    }

    @Test
    void getDriverByLastName_doesItReturnDriverByLastName_okay() {

        when(driverRepository.findByLastName(lastName)).thenReturn(testDrivers);

        List<DriverDto> listOfDriverDto = driverService.getDriverByLastName(lastName);

        assertEquals(listOfDriverDto.size(), testDrivers.size());

    }

    @Test
    void getDriverByFirstAndFLastName_doesItReturnDriverByFirstAndFLastName_okay() {

        when(driverRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(testDrivers);

        List<DriverDto> listOfDriverDto = driverService.getDriverByFirstAndFLastName(firstName, lastName);

        assertEquals(listOfDriverDto.size(), testDrivers.size());

    }

    @Test
    void getDriverByDailyWageLessThan_doesItReturnDriverByDailyWageLessThan_okay() {

        when(driverRepository.findByDailyWageLessThan(dailyWage)).thenReturn(testDrivers);

        List<DriverDto> listOfDriverDto = driverService.getDriverByDailyWageLessThan(dailyWage);

        assertEquals(listOfDriverDto.size(), testDrivers.size());

    }

    @Test
    void getDriverByDailyWageEquals_doesItReturnDriverByDailyWageEquals_okay() {

        when(driverRepository.findByDailyWageEquals(dailyWage)).thenReturn(testDrivers);

        List<DriverDto> listOfDriverDto = driverService.getDriverByDailyWageEquals(dailyWage);

        assertEquals(listOfDriverDto.size(), testDrivers.size());

    }

    @Test
    void getDriverByDailyWageGreaterThan_doesItReturnDriverByDailyWageGreaterThan_okay() {

        when(driverRepository.findByDailyWageGreaterThan(dailyWage)).thenReturn(testDrivers);

        List<DriverDto> listOfDriverDto = driverService.getDriverByDailyWageGreaterThan(dailyWage);

        assertEquals(listOfDriverDto.size(), testDrivers.size());

    }

    @Test
    void getDriverByPhoneNumber_doesItReturnDriverByPhoneNumber_okay() {

        when(driverRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.ofNullable(driver));

        DriverDto expectedDriver = driverService.getDriverByPhoneNumber(phoneNumber);

        assertNotNull(expectedDriver);
    }

    @Test
    void addNewDriver_doesItAddNewDriver_okay() {

        when(driverRepository.save(driver)).thenReturn(driver);

        DriverDto driverDto = driverMapper.mapDriverToDriverDto(driver);

        driverService.addNewDriver(driverDto);

        verify(driverRepository).save(driver);
    }

    @Test
    void getDeleteDriverByPhoneNumber_doesItFindDeleteDriverByPhoneNumberAndVerifyMethod_okay() {

        when(driverRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.ofNullable(driver));

        driverService.deleteDriverByPhoneNumber(phoneNumber);

        verify(driverRepository).delete(driver);
    }

    @Test
    void deleteDriverByPhoneNumber_doesItThrowInvalidUserException_okay() {

        Throwable runtimeException = assertThrows(InvalidUserInputException.class, () ->
                        driverService.deleteDriverByPhoneNumber(wrongPhoneNumber),
                "Incorrect Phone Number Format");

        assertTrue(runtimeException.getMessage().contains("Incorrect Phone"));
    }

    @Test
    void deleteDriverByPhoneNumber_doesItThrowResourceMissingException_okay() {

        Throwable runtimeException = assertThrows(InvalidUserInputException.class, () ->
                        driverService.deleteDriverByPhoneNumber(wrongPhoneNumber),
                "Incorrect Phone Number Format");

        assertTrue(runtimeException.getMessage().contains("Incorrect Phone"));
    }

    @Test
    void getBusServiceByPlateNumberAndDelete_doesItThrowResourceMissingException_okay() {

        Throwable runtimeException = assertThrows(ResourceMissingException.class, () ->
                        driverService.deleteDriverByPhoneNumber(newPhoneNumber),
                "Driver not Found\", \"Driver\"");

        assertTrue(runtimeException.getMessage().contains("not Found"));
    }

    @Test
    void getDriverByPhoneNumber_doesItThrowResourceMissingException_okay() {

        Throwable runtimeException = assertThrows(ResourceMissingException.class, () ->
                        driverService.getDriverByPhoneNumber(newPhoneNumber),
                "Driver not Found\", \"Driver\"");

        assertTrue(runtimeException.getMessage().contains("not Found"));
    }

    @Test
    void getDriverByPhoneNumber_doesItThrowInvalidUserException_okay() {

        Throwable runtimeException = assertThrows(InvalidUserInputException.class, () ->
                        driverService.getDriverByPhoneNumber(wrongPhoneNumber),
                "Incorrect Phone Number Format");

        assertTrue(runtimeException.getMessage().contains("Incorrect Phone"));
    }

    @Test
    void calculateDailyWage_doesItCalculate_okay() {
        when(driverRepository.findAll()).thenReturn(testDrivers);

        double expectedResult = driverService.calculateDailyWage();

        assertEquals(expectedResult, dailyWage);
    }

}