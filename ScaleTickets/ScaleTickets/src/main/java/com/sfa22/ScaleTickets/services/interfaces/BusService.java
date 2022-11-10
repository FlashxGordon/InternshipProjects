package com.sfa22.ScaleTickets.services.interfaces;

import com.sfa22.ScaleTickets.dtos.BusDto;


import java.util.List;
import java.util.Optional;

public interface BusService {

    List<BusDto> getAllBusses();

    BusDto getBusByPlate(String busPlateNumber);

    Optional<BusDto> getBusById(int id);

    List<BusDto> getBusesByCapacityLessThan(int capacity);

    List<BusDto> getBusesByCapacityGreaterThan(int capacity);

    List<BusDto> getBusesByCapacityEquals(int capacity);

    List<BusDto> getByDailyCostLessThan(double dailyCost);

    List<BusDto> getByDailyCostGreaterThan(double dailyCost);

    List<BusDto> getByDailyCostEquals(double dailyCost);

    List<BusDto> getByBusCapacityEqualsAndDailyCostEquals(int capacity, double dailyCost);

    int addNewBus(BusDto busDto);

    void getBusByPlateAndUpdateFixCost(String busPlateNumber, double newDailyCost);

    void getBusServiceByPlateNumberAndDelete(String busPlateNumber);
    
    double getDailyCostOfAllBuses();
}