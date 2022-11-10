package com.sfa22.ScaleTickets.validations;

import com.sfa22.ScaleTickets.entities.Bus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class BusValidation {

    public boolean isBusPlateCorrect(String busPlate) {

        String pattern = "^[A-Z]{1,2}\\d{4}[A-Z]{2}$";

        return Pattern.compile(pattern).matcher(busPlate).matches();
    }

    public boolean isBusPlateAlreadyExists(String busPlate, List<Bus> allBuses) {

        return allBuses.stream().anyMatch(bus -> bus.getBusPlate().equalsIgnoreCase(busPlate));
    }
}