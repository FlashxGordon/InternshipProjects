package com.sfa22.ScaleTickets.databasebootstrapping;

import com.sfa22.ScaleTickets.data.BusRepository;
import com.sfa22.ScaleTickets.data.DriverRepository;
import com.sfa22.ScaleTickets.data.RouteRepository;
import com.sfa22.ScaleTickets.data.TripRepository;
import com.sfa22.ScaleTickets.dtos.TripRequestDto;
import com.sfa22.ScaleTickets.entities.Trip;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Component
public class TripTable implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(TripTable.class);
    private final TripRepository tripRepository;
    private final BusRepository busRepository;
    private final DriverRepository driverRepository;
    private final RouteRepository routeRepository;


    public TripTable(TripRepository tripRepository,
                     BusRepository busRepository,
                     DriverRepository driverRepository,
                     RouteRepository routeRepository) {
        this.tripRepository = tripRepository;
        this.busRepository = busRepository;
        this.driverRepository = driverRepository;
        this.routeRepository = routeRepository;

    }

    /**
     * Method which inserts predetermined values into the database
     * the purpose of inserting the information is to  automatically insert data upon server startup so
     * that the application has data that it can manipulate for demonstration purposes
     * <p>
     * This particular method populates the trip table with trips for a period of 20 days
     *
     * @param args
     */

    @Override
    public void run(String... args) {

        List<Trip> tripList = tripRepository.findAll();

        int dayIncrement1 = 0;
        int dayIncrement2 = 0;

        if (tripList.isEmpty()) {

            logger.info("Bootstrapping trip table...");

            for (int i = 0; i < 20; i++) {


                LocalDateTime departureDateMorning1 = LocalDateTime.now().plusDays(1).toLocalDate().
                        atStartOfDay().plusDays(dayIncrement1).plusHours(8);


                TripRequestDto requestDto = new TripRequestDto(departureDateMorning1, 1, 1, 1);

                saveTrip(requestDto);

                TripRequestDto requestDto2 = new TripRequestDto(departureDateMorning1.plusHours(6), 2, 1, 2);

                saveTrip(requestDto2);

                LocalDateTime departureDateMorning2 = LocalDateTime.now().plusDays(1).toLocalDate().
                        atStartOfDay().plusDays(dayIncrement2).plusHours(8);


                TripRequestDto requestDto3 = new TripRequestDto(departureDateMorning2, 3, 2, 3);


                saveTrip(requestDto3);

                TripRequestDto requestDto4 = new TripRequestDto(departureDateMorning2.plusHours(4), 4, 2, 4);

                saveTrip(requestDto4);

                dayIncrement1++;
                dayIncrement2++;

                logger.info("...Bootstrapping trip table completed");
            }
        }

    }

    private void saveTrip(TripRequestDto requestDto) {
        tripRepository.save
                (new Trip(requestDto.getDepartureDate(),
                        routeRepository.findById(requestDto.getRouteID()).orElseThrow(),
                        busRepository.findById(requestDto.getBusID()).orElseThrow(),
                        driverRepository.findById(requestDto.getDriverID()).orElseThrow()));
    }


}


