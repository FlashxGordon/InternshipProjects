package com.sfa22.ScaleTickets.services.implementations;

import com.sfa22.ScaleTickets.data.BusRepository;
import com.sfa22.ScaleTickets.data.DriverRepository;
import com.sfa22.ScaleTickets.data.RouteRepository;
import com.sfa22.ScaleTickets.data.TripRepository;
import com.sfa22.ScaleTickets.dtos.DetailedTripDto;
import com.sfa22.ScaleTickets.dtos.TripDto;
import com.sfa22.ScaleTickets.dtos.TripRequestDto;
import com.sfa22.ScaleTickets.entities.Bus;
import com.sfa22.ScaleTickets.entities.Driver;
import com.sfa22.ScaleTickets.entities.Route;
import com.sfa22.ScaleTickets.entities.Trip;
import com.sfa22.ScaleTickets.exceptions.InvalidUserInputException;
import com.sfa22.ScaleTickets.exceptions.ResourceMissingException;
import com.sfa22.ScaleTickets.exceptions.UnavailableTransportException;
import com.sfa22.ScaleTickets.mappers.TripMapper;
import com.sfa22.ScaleTickets.services.interfaces.TripService;
import com.sfa22.ScaleTickets.validations.TripValidation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final TripMapper tripMapper;
    private final BusRepository busRepository;
    private final DriverRepository driverRepository;
    private final RouteRepository routeRepository;

    private final TripValidation tripValidation;

    public TripServiceImpl(TripRepository tripRepository, TripMapper tripMapper, BusRepository busRepository,
                           DriverRepository driverRepository, RouteRepository routeRepository, TripValidation tripValidation) {

        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
        this.busRepository = busRepository;
        this.driverRepository = driverRepository;
        this.routeRepository = routeRepository;
        this.tripValidation = tripValidation;
    }

    @Override
    public TripDto getTrip(int id) {
        return tripMapper.mapTripToTripDto(tripRepository.findById(id).orElseThrow(() -> new ResourceMissingException(
                "Trip not found", "Trip")));
    }

    @Override
    public List<TripDto> getByDepartureDateAndFilters(LocalDate departureDate, String departureCity, String arrivalCity,
                                                      Integer remainingSeats) {
        return tripMapper
                .mapListOfTripToTripDto(tripRepository
                        .findByDepartureDateAndDepartureCityAndArrivalCityAndRemainingSeats
                                (departureDate, departureCity, arrivalCity, remainingSeats != null ? remainingSeats : 0));
    }

    @Override
    public List<TripDto> getByDepartureDateGreaterThanEqualAndFilters(LocalDate departureDate, String departureCity,
                                                                      String arrivalCity, Integer remainingSeats) {
        return tripMapper.
                mapListOfTripToTripDto(tripRepository
                        .findByDepartureDateGreaterThanEqualAndDepartureCityAndArrivalCityAndRemainingSeats
                                (departureDate, departureCity, arrivalCity, remainingSeats != null ? remainingSeats : 0));
    }

    @Override
    public DetailedTripDto getDetailedTrip(int id) {
        return tripMapper.mapTripToDetailedTripDto(tripRepository.findById(id).orElseThrow(() -> new ResourceMissingException("Trip not found", "Trip")));
    }

    @Override
    public List<DetailedTripDto> getDetailedTripsByDepartureDateAndFilters(LocalDate departureDate, String departureCity
            , String arrivalCity, Integer remainingSeats) {
        return tripMapper
                .mapListOfTripToDetailedTripDto(tripRepository
                        .findByDepartureDateAndDepartureCityAndArrivalCityAndRemainingSeats
                                (departureDate, departureCity, arrivalCity, remainingSeats != null ? remainingSeats : 0));
    }

    @Override
    public List<DetailedTripDto> getAllDetailedTrips() {
        return tripMapper.mapListOfTripToDetailedTripDto(tripRepository.findAll());
    }

    @Override
    public List<DetailedTripDto> getDetailedTripsByRoute(int routeId) {
        return tripMapper.mapListOfTripToDetailedTripDto(tripRepository.findByRoute_RouteId(routeId));
    }

    @Override
    public List<DetailedTripDto> getDetailedTripsByBus(int busId) {
        return tripMapper.mapListOfTripToDetailedTripDto(getTripByBusId(busId));
    }

    @Override
    public List<DetailedTripDto> getDetailedTripsByDriver(int driverId) {
        return tripMapper.mapListOfTripToDetailedTripDto(getTripByDriverId(driverId));
    }

    @Override
    public List<DetailedTripDto> getDetailedTripsByDepartureDateGreaterThanEqualAndFilters(LocalDate departureDate,
                                                                                           String departureCity, String arrivalCity, Integer remainingSeats) {
        return tripMapper.
                mapListOfTripToDetailedTripDto(tripRepository
                        .findByDepartureDateGreaterThanEqualAndDepartureCityAndArrivalCityAndRemainingSeats
                                (departureDate, departureCity, arrivalCity, remainingSeats != null ? remainingSeats : 0));
    }

    @Override
    public int addTrip(TripRequestDto tripRequestDto) {
        Route route = routeRepository.findById(tripRequestDto.getRouteID())
                .orElseThrow(() -> new ResourceMissingException("Route not found", "Route"));
        Bus bus = busRepository.findById(tripRequestDto.getBusID())
                .orElseThrow(() -> new ResourceMissingException("Bus not found", "Bus"));
        Driver driver = driverRepository.findById(tripRequestDto.getDriverID())
                .orElseThrow(() -> new ResourceMissingException("Driver not found", "Driver"));

        Trip tripToAdd = tripMapper.mapTripRequestDtoToTrip(tripRequestDto, route, bus, driver);

        if (!tripValidation.isDepartureDateBeforeArrivalDate(tripToAdd.getDepartureDate(), tripToAdd.getArrivalDate())) {
            throw new InvalidUserInputException("Arrival date should be after departure date");
        }
        if (!tripValidation.isFreeForNewTrip(tripToAdd.getDepartureDate(), tripToAdd.getArrivalDate(),
                getTripByBusId(bus.getBusID()))) {
            throw new UnavailableTransportException("Bus not free for that period of time");
        }
        if (!tripValidation.isFreeForNewTrip(tripToAdd.getDepartureDate(), tripToAdd.getArrivalDate(),
                getTripByDriverId(driver.getDriverID()))) {
            throw new UnavailableTransportException("Driver not free for that period of time");
        }

        Trip trip = tripRepository.save(tripToAdd);

        return trip.getTripId();
    }

    @Override
    public int updateTrip(int idForUpdate, TripRequestDto tripRequestDto) {
        Route route = routeRepository.findById(tripRequestDto.getRouteID())
                .orElseThrow(() -> new ResourceMissingException("Route not found", "Route"));
        Bus bus = busRepository.findById(tripRequestDto.getBusID())
                .orElseThrow(() -> new ResourceMissingException("Bus not found", "Bus"));
        Driver driver = driverRepository.findById(tripRequestDto.getDriverID())
                .orElseThrow(() -> new ResourceMissingException("Driver not found", "Driver"));
        List<Trip> busTrips = getTripByBusId(bus.getBusID()).stream()
                .filter(trip -> trip.getTripId() != idForUpdate).collect(Collectors.toList());
        List<Trip> driverTrips = getTripByDriverId(driver.getDriverID()).stream()
                .filter(trip -> trip.getTripId() != idForUpdate).collect(Collectors.toList());

        Trip oldTrip = tripRepository.findById(idForUpdate)
                .orElseThrow(() -> new ResourceMissingException("Trip not found", "Trip"));
        Trip tripToUpdate = tripMapper.mapTripRequestDtoToTrip(idForUpdate, tripRequestDto, route, bus, driver);

        if (!tripValidation.isDepartureDateBeforeArrivalDate(tripToUpdate.getDepartureDate(),
                tripToUpdate.getArrivalDate())) {
            throw new InvalidUserInputException("Arrival date should be after departure date");
        }
        if (!tripValidation.isFreeForNewTrip(tripToUpdate.getDepartureDate(), tripToUpdate.getArrivalDate(), busTrips)) {
            throw new UnavailableTransportException("Bus not free for that period of time");
        }
        if (!tripValidation.isFreeForNewTrip(tripToUpdate.getDepartureDate(), tripToUpdate.getArrivalDate(), driverTrips)) {
            throw new UnavailableTransportException("Driver not free for that period of time");
        }
        if (!tripValidation.isNewBusSeatCountEqualOrHigherThanSoldSeats(oldTrip, bus)) {
            throw new InvalidUserInputException("New bus does not have the capacity for all of the sold tickets.");
        }

        tripToUpdate.setRemainingSeats(tripToUpdate.getRemainingSeats() - (oldTrip.getBus().getBusCapacity() - oldTrip.getRemainingSeats()));
        Trip updatedTrip = tripRepository.save(tripToUpdate);

        return updatedTrip.getTripId();
    }

    @Override
    public void deleteTrip(int id) {
        if (!tripRepository.existsById(id)) {
            throw new ResourceMissingException("Trip not found", "Trip");
        }
        tripRepository.deleteById(id);
    }

    private List<Trip> getTripByBusId(int busId) {
        return tripRepository.findByBus_BusID(busId);
    }

    private List<Trip> getTripByDriverId(int driverId) {
        return tripRepository.findByDriver_DriverID(driverId);
    }
}
