package com.sfa22.ScaleTickets.services.implementations;

import com.sfa22.ScaleTickets.data.BusRepository;
import com.sfa22.ScaleTickets.dtos.BusDto;
import com.sfa22.ScaleTickets.dtos.DriverDto;
import com.sfa22.ScaleTickets.entities.Bus;
import com.sfa22.ScaleTickets.entities.Driver;
import com.sfa22.ScaleTickets.entities.Ticket;
import com.sfa22.ScaleTickets.exceptions.AlreadyExistsException;
import com.sfa22.ScaleTickets.exceptions.InvalidUserInputException;
import com.sfa22.ScaleTickets.exceptions.ResourceMissingException;
import com.sfa22.ScaleTickets.mappers.BusMappers;
import com.sfa22.ScaleTickets.services.interfaces.BusService;
import com.sfa22.ScaleTickets.validations.BusValidation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    private final BusMappers busMappers;

    private final BusValidation busValidation;

    public BusServiceImpl(BusRepository busRepository, BusMappers busMappers, BusValidation busValidation) {
        this.busRepository = busRepository;
        this.busMappers = busMappers;
        this.busValidation = busValidation;
    }

    @Override
    public List<BusDto> getAllBusses() {

        List<Bus> busses = busRepository.findAll();

        return busMappers.mapListOfBusToBusDto(busses);
    }

    @Override
    public BusDto getBusByPlate(String busPlateNumber) {

        return busMappers.mapBusToBusDto(busRepository.findByBusPlate(busPlateNumber).orElseThrow(() ->
                new ResourceMissingException("Bus not Found", "Bus")));
    }

    @Override
    public Optional<BusDto> getBusById(int id) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceMissingException("Bus not found", "Bus"));

        return Optional.ofNullable(busMappers.mapBusToBusDto(bus));
    }

    @Override
    public List<BusDto> getBusesByCapacityLessThan(int capacity) {

        List<Bus> searchedBuses = busRepository.findByBusCapacityLessThan(capacity);

        return busMappers.mapListOfBusToBusDto(searchedBuses);
    }

    @Override
    public List<BusDto> getBusesByCapacityGreaterThan(int capacity) {

        List<Bus> searchedBuses = busRepository.findByBusCapacityGreaterThan(capacity);

        return busMappers.mapListOfBusToBusDto(searchedBuses);
    }

    @Override
    public List<BusDto> getBusesByCapacityEquals(int capacity) {

        List<Bus> searchedBuses = busRepository.findByBusCapacityEquals(capacity);

        return busMappers.mapListOfBusToBusDto(searchedBuses);
    }

    @Override
    public List<BusDto> getByDailyCostLessThan(double dailyCost) {

        List<Bus> searchedBuses = busRepository.findByDailyCostLessThan(dailyCost);

        return busMappers.mapListOfBusToBusDto(searchedBuses);
    }

    @Override
    public List<BusDto> getByDailyCostGreaterThan(double dailyCost) {

        List<Bus> searchedBuses = busRepository.findByDailyCostGreaterThan(dailyCost);

        return busMappers.mapListOfBusToBusDto(searchedBuses);
    }

    @Override
    public List<BusDto> getByDailyCostEquals(double dailyCost) {

        List<Bus> searchedBuses = busRepository.findByDailyCostEquals(dailyCost);

        return busMappers.mapListOfBusToBusDto(searchedBuses);
    }

    @Override
    public List<BusDto> getByBusCapacityEqualsAndDailyCostEquals(int capacity, double dailyCost) {

        List<Bus> searchedBuses = busRepository.findByBusCapacityEqualsAndDailyCostEquals(capacity, dailyCost);

        return busMappers.mapListOfBusToBusDto(searchedBuses);
    }

    @Override
    public int addNewBus(BusDto busDto) {

        isPlateNumberCorrect(busDto.getBusPlate());

        isBusPlateAlreadyExists(busDto.getBusPlate(), busRepository.findAll());

        Bus newBus = busMappers.mapBusDtoToBus(busDto);

        busRepository.save(newBus);

        return newBus.getBusID();
    }

    @Override
    public void getBusByPlateAndUpdateFixCost(String busPlateNumber, double newDailyCost) {

        isPlateNumberCorrect(busPlateNumber);

        Optional<Bus> optionalBus = busRepository.findByBusPlate(busPlateNumber);

        Bus searchedBus = optionalBus.orElseThrow(() -> new ResourceMissingException("Bus not Found", "Bus"));

        searchedBus.setDailyCost(newDailyCost);

        busRepository.save(searchedBus);
    }

    @Override
    public void getBusServiceByPlateNumberAndDelete(String busPlateNumber) {

        isPlateNumberCorrect(busPlateNumber);

        Optional<Bus> optionalBus = busRepository.findByBusPlate(busPlateNumber);

        Bus searchedBus = optionalBus.orElseThrow(() -> new ResourceMissingException("Bus not Found", "Bus"));

        busRepository.delete(searchedBus);
    }

    @Override
    public double getDailyCostOfAllBuses() {

        return busRepository.findAll().stream().mapToDouble(Bus::getDailyCost).sum();
    }

    private void isPlateNumberCorrect(String busPlate) {

        if (!busValidation.isBusPlateCorrect(busPlate)) {

            throw new InvalidUserInputException("Incorrect Number Plate Format");
        }
    }

    private void isBusPlateAlreadyExists(String busPlate, List<Bus> allBuses) {

        if (busValidation.isBusPlateAlreadyExists(busPlate, allBuses)) {

            throw new AlreadyExistsException("Already Existing Bus Plate");
        }
    }
}