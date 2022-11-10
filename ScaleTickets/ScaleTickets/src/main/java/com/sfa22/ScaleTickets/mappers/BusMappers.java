package com.sfa22.ScaleTickets.mappers;

import com.sfa22.ScaleTickets.dtos.BusDto;
import com.sfa22.ScaleTickets.entities.Bus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BusMappers {

    public Bus mapBusDtoToBus(BusDto busDto) {

        return new Bus(busDto.getBusID(), busDto.getBusPlate(), busDto.getBusCapacity(), busDto.getDailyCost());
    }

    public BusDto mapBusToBusDto(Bus bus) {

        return new BusDto(bus.getBusID(), bus.getBusPlate(), bus.getBusCapacity(), bus.getDailyCost());
    }

    public List<BusDto> mapListOfBusToBusDto(List<Bus> listOfBuses) {

        return listOfBuses.stream().map(this::mapBusToBusDto).collect(Collectors.toList());
    }
}