package com.sfa22.ScaleTickets.services;

import com.sfa22.ScaleTickets.data.BusRepository;
import com.sfa22.ScaleTickets.dtos.BusDto;
import com.sfa22.ScaleTickets.dtos.DisplayTicketDto;
import com.sfa22.ScaleTickets.entities.Bus;
import com.sfa22.ScaleTickets.exceptions.AlreadyExistsException;
import com.sfa22.ScaleTickets.exceptions.InvalidUserInputException;
import com.sfa22.ScaleTickets.exceptions.ResourceMissingException;
import com.sfa22.ScaleTickets.mappers.BusMappers;
import com.sfa22.ScaleTickets.services.implementations.BusServiceImpl;
import com.sfa22.ScaleTickets.validations.BusValidation;
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
public class BusServiceImplTests {

    @Mock
    private BusRepository busRepository;

    @Spy
    private BusMappers busMappers;

    @Spy
    private BusValidation busValidation;

    @InjectMocks
    private BusServiceImpl busServiceImpl;

    private int busID, ID;

    private String busPlate;

    private String wrongBusPlate;

    private int busCapacity;

    private double busDailyCost;

    private double newBusDailyCost;

    private Bus testBusOne;

    private Bus testBusTwo;

    private Bus testBusThree;

    private BusDto busDtoWithWrongPlate;

    private BusDto testBusDtoOne;

    private List<Bus> listOfTestBuses;

    @BeforeEach
    public void setUp() {

        busID = 0;

        busPlate = "PB0000BP";

        wrongBusPlate = "15PB15BP";

        busCapacity = 40;

        busDailyCost = 215.48;

        newBusDailyCost = 500.00;

        testBusOne = new Bus(busID, busPlate, busCapacity, busDailyCost);

        testBusTwo = new Bus(busID, busPlate, busCapacity, newBusDailyCost);

        busDtoWithWrongPlate = new BusDto(busID, wrongBusPlate, busCapacity, newBusDailyCost);

        testBusDtoOne = new BusDto(busID, busPlate, busCapacity, newBusDailyCost);

        listOfTestBuses = new ArrayList<>();

        listOfTestBuses.add(testBusOne);

        listOfTestBuses.add(testBusTwo);


    }

    @Test
    void getAllBusses_doesItReturnAllBusses_okay() {

        when(busRepository.findAll()).thenReturn(listOfTestBuses);

        List<BusDto> busDtoList = busServiceImpl.getAllBusses();

        assertEquals(busDtoList.size(), listOfTestBuses.size());

    }

    @Test
    void getById_doesItReturnBusById_okay() {

        when(busRepository.findById(busID)).thenReturn(Optional.ofNullable(testBusTwo));

        Optional<BusDto> foundBus = busServiceImpl.getBusById(busID);

        Assertions.assertNotNull(foundBus);

    }

    @Test
    void getBusByPlate_doesItReturnBusByPlate_okay() {

        when(busRepository.findByBusPlate(busPlate)).thenReturn(Optional.ofNullable(testBusOne));

        BusDto expectedBus = busServiceImpl.getBusByPlate(busPlate);

        assertNotNull(expectedBus);
    }

    @Test
    void getBusByPlate_doesItResourceMissingException_okay() {

        Throwable runtimeException = assertThrows(ResourceMissingException.class, () ->
                busServiceImpl.getBusByPlate(wrongBusPlate), "Bus not Found\", \"Bus");

        assertTrue(runtimeException.getMessage().contains("not Found"));
    }

    @Test
    void getBusesByCapacityLessThan_doesItReturnBusesByCapacityLessThan_okay() {

        when(busRepository.findByBusCapacityLessThan(busCapacity)).thenReturn(listOfTestBuses);

        List<BusDto> expectedBuses = busServiceImpl.getBusesByCapacityLessThan(busCapacity);

        assertEquals(expectedBuses.size(), listOfTestBuses.size());
    }

    @Test
    void getBusesByCapacityGreaterThan_doesItReturnBusesByCapacityGreaterThan_okay() {

        when(busRepository.findByBusCapacityGreaterThan(busCapacity)).thenReturn(listOfTestBuses);

        List<BusDto> expectedBuses = busServiceImpl.getBusesByCapacityGreaterThan(busCapacity);

        assertEquals(expectedBuses.size(), listOfTestBuses.size());
    }

    @Test
    void getBusesByCapacityEquals_doesItReturnBusesByCapacityEqual_okay() {

        when(busRepository.findByBusCapacityEquals(busCapacity)).thenReturn(listOfTestBuses);

        List<BusDto> expectedBuses = busServiceImpl.getBusesByCapacityEquals(busCapacity);

        assertEquals(expectedBuses.size(), listOfTestBuses.size());
    }

    @Test
    void getByDailyCostLessThan_doesItReturnBusesByDailyCostLessThan_okay() {

        when(busRepository.findByDailyCostLessThan(busDailyCost)).thenReturn(listOfTestBuses);

        List<BusDto> expectedBuses = busServiceImpl.getByDailyCostLessThan(busDailyCost);

        assertEquals(expectedBuses.size(), listOfTestBuses.size());
    }

    @Test
    void getByDailyCostGreaterThan_doesItReturnBusesByDailyCostGreaterThan_okay() {

        when(busRepository.findByDailyCostGreaterThan(busDailyCost)).thenReturn(listOfTestBuses);

        List<BusDto> expectedBuses = busServiceImpl.getByDailyCostGreaterThan(busDailyCost);

        assertEquals(expectedBuses.size(), listOfTestBuses.size());
    }

    @Test
    void getByDailyCostEquals_doesItReturnBusesByDailyCostEquals_okay() {

        when(busRepository.findByDailyCostEquals(busDailyCost)).thenReturn(listOfTestBuses);

        List<BusDto> expectedBuses = busServiceImpl.getByDailyCostEquals(busDailyCost);

        assertEquals(expectedBuses.size(), listOfTestBuses.size());
    }

    @Test
    void getByBusCapacityEqualsAndDailyCostEquals_doesItReturnBusesByCapacityAndDailyCostEquals_okay() {

        when(busRepository.findByBusCapacityEqualsAndDailyCostEquals(busCapacity, busDailyCost)).thenReturn(listOfTestBuses);

        List<BusDto> expectedBuses = busServiceImpl.getByBusCapacityEqualsAndDailyCostEquals(busCapacity, busDailyCost);

        assertEquals(expectedBuses.size(), listOfTestBuses.size());
    }

    @Test
    void addNewBus_doesItAddNewBus_okay() {

        when(busRepository.save(testBusOne)).thenReturn(testBusOne);

        BusDto busDto = busMappers.mapBusToBusDto(testBusOne);

        busServiceImpl.addNewBus(busDto);

        verify(busRepository).save(testBusOne);
    }

    @Test
    void addNewBus_doesItThrowInvalidUserException_okay() {

        Throwable runtimeException = assertThrows(InvalidUserInputException.class, () ->
                busServiceImpl.addNewBus(busDtoWithWrongPlate), "Incorrect Number Plate Format");

        assertTrue(runtimeException.getMessage().contains("Incorrect Number Plate"));
    }

    @Test
    void addNewBus_doesItThrowAlreadyExistsException_okay() {

        when(busRepository.findAll()).thenReturn(listOfTestBuses);

        Throwable runtimeException = assertThrows(AlreadyExistsException.class, () ->
                busServiceImpl.addNewBus(testBusDtoOne), "Already Existing Bus Plate");

        assertTrue(runtimeException.getMessage().contains("Already Existing"));
    }

    @Test
    void getBusByPlateAndUpdateFixCost_doesItFindBusByPlateAndVerifyMethod_okay() {

        when(busRepository.findByBusPlate(busPlate)).thenReturn(Optional.ofNullable(testBusOne));

        busServiceImpl.getBusByPlateAndUpdateFixCost(busPlate, newBusDailyCost);

        verify(busRepository).save(testBusTwo);
    }

    @Test
    void getBusByPlateAndUpdateFixCost_doesItThrowInvalidUserException_okay() {

        Throwable runtimeException = assertThrows(InvalidUserInputException.class, () ->
                        busServiceImpl.getBusByPlateAndUpdateFixCost(wrongBusPlate, newBusDailyCost),
                "Incorrect Number Plate Format");

        assertTrue(runtimeException.getMessage().contains("Incorrect Number Plate"));
    }

    @Test
    void getBusServiceByPlateNumberAndDelete_doesItFindBusByPlateAndVerifyMethod_okay() {

        when(busRepository.findByBusPlate(busPlate)).thenReturn(Optional.ofNullable(testBusOne));

        busServiceImpl.getBusServiceByPlateNumberAndDelete(busPlate);

        verify(busRepository).delete(testBusOne);
    }

    @Test
    void getBusServiceByPlateNumberAndDelete_doesItThrowInvalidUserException_okay() {

        Throwable runtimeException = assertThrows(InvalidUserInputException.class, () ->
                        busServiceImpl.getBusServiceByPlateNumberAndDelete(wrongBusPlate),
                "Incorrect Number Plate Format");

        assertTrue(runtimeException.getMessage().contains("Incorrect Number Plate"));
    }


    @Test
    void getDailyCostOfAllBuses_doesItCalculateCorrectly_okay() {

        when(busRepository.findAll()).thenReturn(listOfTestBuses);

        double expectedSum = busDailyCost + newBusDailyCost;

        double result = busServiceImpl.getDailyCostOfAllBuses();

        assertEquals(expectedSum, result);
    }

    @Test
    void getDailyCostOfAllBuses_doesItCalculateCorrectly_notOkay() {

        when(busRepository.findAll()).thenReturn(listOfTestBuses);

        double notExpectedSum = 100;

        double result = busServiceImpl.getDailyCostOfAllBuses();

        assertNotEquals(notExpectedSum, result);
    }
}