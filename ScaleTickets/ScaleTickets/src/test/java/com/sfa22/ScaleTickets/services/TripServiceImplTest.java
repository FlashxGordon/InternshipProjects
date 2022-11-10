package com.sfa22.ScaleTickets.services;

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
import com.sfa22.ScaleTickets.services.implementations.TripServiceImpl;
import com.sfa22.ScaleTickets.validations.TripValidation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TripServiceImplTest {
    @Spy
    TripMapper tripMapper;

    @Mock
    BusRepository busRepository;
    @Mock
    DriverRepository driverRepository;
    @Mock
    RouteRepository routeRepository;
    @Mock
    TripRepository tripRepository;
    @Mock
    TripValidation tripValidation;

    @InjectMocks
    TripServiceImpl tripService;

    private final static List<Bus> buses = new ArrayList<>();
    private final static List<TripDto.BusDto> busDtos = new ArrayList<>();
    private final static List<Driver> drivers = new ArrayList<>();
    private final static List<Route> routes = new ArrayList<>();
    private final static List<TripDto.RouteDto> routeDtos = new ArrayList<>();
    private final static List<Trip> trips = new ArrayList<>();
    private final static List<TripDto> tripDtos = new ArrayList<>();
    private final static List<DetailedTripDto> detailedTripDtos = new ArrayList<>();

    @BeforeAll
    public static void setup() {
        buses.add(new Bus(1, "CA0023BP", 23, 14.3));
        buses.add(new Bus(2, "CE1023BV", 25, 26.6));
        busDtos.add(new TripDto.BusDto(1, "CA0023BP"));
        busDtos.add(new TripDto.BusDto(2, "CE1023BV"));
        drivers.add(new Driver(1, "Ivan", "Ivanov", "0878123456", 13.2));
        drivers.add(new Driver(2, "Mitko", "Mitkov", "087812345", 53.2));
        routes.add(new Route(1, "Plovdiv", "Sofia", 14, Duration.parse("PT3H10M"), 130));
        routes.add(new Route(2, "Plovdiv", "Burgas", 18, Duration.parse("PT5H10M"), 250));
        routes.add(new Route(3, "Sofia", "Plovdiv", 15, Duration.parse("PT3H10M"), 130));
        routeDtos.add(new TripDto.RouteDto(1, "Plovdiv", "Sofia", 14, Duration.parse("PT3H10M"), 130));
        routeDtos.add(new TripDto.RouteDto(2, "Plovdiv", "Burgas", 18, Duration.parse("PT5H10M"), 250));
        routeDtos.add(new TripDto.RouteDto(3, "Sofia", "Plovdiv", 15, Duration.parse("PT3H10M"), 130));

        trips.add(new Trip(1, LocalDateTime.parse("2021-07-13T21:00:00"), routes.get(0), buses.get(1), drivers.get(1)));
        trips.add(new Trip(2, LocalDateTime.parse("2023-12-20T08:00:00"), routes.get(1), buses.get(0), drivers.get(1)));
        trips.add(new Trip(3, LocalDateTime.parse("2025-11-18T03:00:00"), routes.get(2), buses.get(0), drivers.get(0)));
        trips.add(new Trip(4, LocalDateTime.parse("2026-08-07T17:00:00"), routes.get(0), buses.get(0), drivers.get(1)));
        trips.add(new Trip(5, LocalDateTime.parse("2027-01-12T04:00:00"), routes.get(1), buses.get(1), drivers.get(1)));

        tripDtos.add(new TripDto(1, LocalDateTime.parse("2021-07-13T21:00:00"),
                LocalDateTime.parse("2021-07-14T00:10:00"), 25, routeDtos.get(0), busDtos.get(1)));
        tripDtos.add(new TripDto(2, LocalDateTime.parse("2023-12-20T08:00:00"),
                LocalDateTime.parse("2023-12-20T13:10:00"), 23, routeDtos.get(1), busDtos.get(0)));
        tripDtos.add(new TripDto(3, LocalDateTime.parse("2025-11-18T03:00:00"),
                LocalDateTime.parse("2025-11-18T06:10:00"), 23, routeDtos.get(2), busDtos.get(0)));
        tripDtos.add(new TripDto(4, LocalDateTime.parse("2026-08-07T17:00:00"),
                LocalDateTime.parse("2026-08-07T20:10:00"), 23, routeDtos.get(0), busDtos.get(0)));
        tripDtos.add(new TripDto(5, LocalDateTime.parse("2027-01-12T04:00:00"),
                LocalDateTime.parse("2027-01-12T09:10:00"), 25, routeDtos.get(1), busDtos.get(1)));

        detailedTripDtos.add(new DetailedTripDto(1, LocalDateTime.parse("2021-07-13T21:00:00"), LocalDateTime.parse("2021" +
                "-07-14T00:10:00"), 25, routes.get(0), buses.get(1), drivers.get(1)));
        detailedTripDtos.add(new DetailedTripDto(2, LocalDateTime.parse("2023-12-20T08:00:00"), LocalDateTime.parse("2023" +
                "-12-20T13:10:00"), 23, routes.get(1), buses.get(0), drivers.get(1)));
        detailedTripDtos.add(new DetailedTripDto(3, LocalDateTime.parse("2025-11-18T03:00:00"), LocalDateTime.parse("2025" +
                "-11-18T06:10:00"), 23, routes.get(2), buses.get(0), drivers.get(0)));
        detailedTripDtos.add(new DetailedTripDto(4, LocalDateTime.parse("2026-08-07T17:00:00"), LocalDateTime.parse("2026" +
                "-08-07T20:10:00"), 23, routes.get(0), buses.get(0), drivers.get(1)));
        detailedTripDtos.add(new DetailedTripDto(5, LocalDateTime.parse("2027-01-12T04:00:00"), LocalDateTime.parse("2027" +
                "-01-12T09:10:00"), 25, routes.get(1), buses.get(1), drivers.get(1)));
    }

    @Test
    void getTrip_existingId_okay() {
        when(tripRepository.findById(3)).thenReturn(Optional.of(trips.get(2)));

        TripDto result = tripService.getTrip(3);

        assertEquals(tripDtos.get(2), result);
    }

    @Test
    void getByDepartureDateAndFilters_noFilters_okay() {
        when(tripRepository
                .findByDepartureDateAndDepartureCityAndArrivalCityAndRemainingSeats(null, null, null, 0))
                .thenReturn(trips);

        List<TripDto> result = tripService.getByDepartureDateAndFilters(null, null, null, null);

        assertEquals(tripDtos, result);
    }

    @Test
    void getByDepartureDateAndFilters_someFilters_okay() {
        when(tripRepository
                .findByDepartureDateAndDepartureCityAndArrivalCityAndRemainingSeats(LocalDate.parse("2026-08-07"), null,
                        "Sofia", 0))
                .thenReturn(trips.subList(3, 3));

        List<TripDto> result = tripService.getByDepartureDateAndFilters(LocalDate.parse("2026-08-07"), null, "Sofia", null);

        assertEquals(tripDtos.subList(3, 3), result);
    }

    @Test
    void getByDepartureDateAndFilters_allFilters_okay() {
        when(tripRepository
                .findByDepartureDateAndDepartureCityAndArrivalCityAndRemainingSeats(LocalDate.parse("2026-08-07"),
                        "Plovdiv", "Sofia", 12))
                .thenReturn(trips.subList(3, 3));

        List<TripDto> result = tripService.getByDepartureDateAndFilters(LocalDate.parse("2026-08-07"), "Plovdiv", "Sofia"
                , 12);

        assertEquals(tripDtos.subList(3, 3), result);
    }

    @Test
    void getByDepartureDateGreaterThanEqualAndFilters_noFilters_okay() {
        when(tripRepository
                .findByDepartureDateGreaterThanEqualAndDepartureCityAndArrivalCityAndRemainingSeats(null, null, null, 0))
                .thenReturn(trips);

        List<TripDto> result = tripService.getByDepartureDateGreaterThanEqualAndFilters(null, null, null, null);

        assertEquals(tripDtos, result);
    }

    @Test
    void getByDepartureDateGreaterThanEqualAndFilters_someFilters_okay() {
        List<Trip> tripsToReturn = new ArrayList<>();
        tripsToReturn.add(trips.get(1));
        tripsToReturn.add(trips.get(3));
        tripsToReturn.add(trips.get(4));

        List<TripDto> expectedTripDtos = new ArrayList<>();
        expectedTripDtos.add(tripDtos.get(1));
        expectedTripDtos.add(tripDtos.get(3));
        expectedTripDtos.add(tripDtos.get(4));

        when(tripRepository
                .findByDepartureDateGreaterThanEqualAndDepartureCityAndArrivalCityAndRemainingSeats(LocalDate.parse("2022" +
                        "-01-01"), "Plovdiv", null, 5))
                .thenReturn(tripsToReturn);

        List<TripDto> result = tripService.getByDepartureDateGreaterThanEqualAndFilters(LocalDate.parse("2022-01-01"),
                "Plovdiv", null, 5);

        assertEquals(expectedTripDtos, result);
    }

    @Test
    void getByDepartureDateGreaterThanEqualAndFilters_allFilters_okay() {
        List<Trip> tripsToReturn = new ArrayList<>();
        tripsToReturn.add(trips.get(1));
        tripsToReturn.add(trips.get(4));

        List<TripDto> expectedTripDtos = new ArrayList<>();
        expectedTripDtos.add(tripDtos.get(1));
        expectedTripDtos.add(tripDtos.get(4));

        when(tripRepository
                .findByDepartureDateGreaterThanEqualAndDepartureCityAndArrivalCityAndRemainingSeats(LocalDate.parse("2022" +
                        "-01-01"), "Plovdiv", "Burgas", 5))
                .thenReturn(tripsToReturn);

        List<TripDto> result = tripService.getByDepartureDateGreaterThanEqualAndFilters(LocalDate.parse("2022-01-01"),
                "Plovdiv", "Burgas", 5);

        assertEquals(expectedTripDtos, result);
    }

    @Test
    void getDetailedTrip_existingId_okay() {
        when(tripRepository.findById(3)).thenReturn(Optional.of(trips.get(2)));

        DetailedTripDto result = tripService.getDetailedTrip(3);

        assertEquals(detailedTripDtos.get(2), result);
    }

    @Test
    void getDetailedTripsByDepartureDateAndFilters_noFilters_okay() {
        when(tripRepository
                .findByDepartureDateAndDepartureCityAndArrivalCityAndRemainingSeats(null, null, null, 0))
                .thenReturn(trips);

        List<DetailedTripDto> result = tripService.getDetailedTripsByDepartureDateAndFilters(null, null, null, null);

        assertEquals(detailedTripDtos, result);
    }

    @Test
    void getDetailedTripsByDepartureDateAndFilters_someFilters_okay() {
        when(tripRepository
                .findByDepartureDateAndDepartureCityAndArrivalCityAndRemainingSeats(LocalDate.parse("2026-08-07"), null,
                        "Sofia", 0))
                .thenReturn(trips.subList(3, 3));

        List<DetailedTripDto> result = tripService.getDetailedTripsByDepartureDateAndFilters(LocalDate.parse("2026-08-07")
                , null, "Sofia", null);

        assertEquals(detailedTripDtos.subList(3, 3), result);
    }

    @Test
    void getDetailedTripsByDepartureDateAndFilters_allFilters_okay() {
        when(tripRepository
                .findByDepartureDateAndDepartureCityAndArrivalCityAndRemainingSeats(LocalDate.parse("2026-08-07"),
                        "Plovdiv", "Sofia", 12))
                .thenReturn(trips.subList(3, 3));

        List<DetailedTripDto> result = tripService.getDetailedTripsByDepartureDateAndFilters(LocalDate.parse("2026-08-07")
                , "Plovdiv", "Sofia"
                , 12);

        assertEquals(detailedTripDtos.subList(3, 3), result);
    }

    @Test
    void getAllDetailedTrips_methodCall_okay() {
        when(tripRepository
                .findAll())
                .thenReturn(trips);

        List<DetailedTripDto> result = tripService.getAllDetailedTrips();

        assertEquals(detailedTripDtos, result);
    }

    @Test
    void getDetailedTripsByRoute_existingId_okay() {
        List<DetailedTripDto> expectedDetailedTripDtos = new ArrayList<>();
        expectedDetailedTripDtos.add(detailedTripDtos.get(0));
        expectedDetailedTripDtos.add(detailedTripDtos.get(3));

        List<Trip> tripsToReturn = new ArrayList<>();
        tripsToReturn.add(trips.get(0));
        tripsToReturn.add(trips.get(3));

        when(tripRepository
                .findByRoute_RouteId(2))
                .thenReturn(tripsToReturn);

        List<DetailedTripDto> result = tripService.getDetailedTripsByRoute(2);

        assertEquals(expectedDetailedTripDtos, result);
    }

    @Test
    void getDetailedTripsByBus_existingId_okay() {
        List<DetailedTripDto> expectedDetailedTripDtos = new ArrayList<>();
        expectedDetailedTripDtos.add(detailedTripDtos.get(0));
        expectedDetailedTripDtos.add(detailedTripDtos.get(4));

        List<Trip> tripsToReturn = new ArrayList<>();
        tripsToReturn.add(trips.get(0));
        tripsToReturn.add(trips.get(4));

        when(tripRepository
                .findByBus_BusID(2))
                .thenReturn(tripsToReturn);

        List<DetailedTripDto> result = tripService.getDetailedTripsByBus(2);

        assertEquals(expectedDetailedTripDtos, result);
    }

    @Test
    void getDetailedTripsByDriver_existingId_okay() {
        List<DetailedTripDto> expectedDetailedTripDtos = new ArrayList<>();
        expectedDetailedTripDtos.add(detailedTripDtos.get(0));
        expectedDetailedTripDtos.add(detailedTripDtos.get(1));
        expectedDetailedTripDtos.add(detailedTripDtos.get(3));
        expectedDetailedTripDtos.add(detailedTripDtos.get(4));

        List<Trip> tripsToReturn = new ArrayList<>();
        tripsToReturn.add(trips.get(0));
        tripsToReturn.add(trips.get(1));
        tripsToReturn.add(trips.get(3));
        tripsToReturn.add(trips.get(4));

        when(tripRepository
                .findByDriver_DriverID(2))
                .thenReturn(tripsToReturn);

        List<DetailedTripDto> result = tripService.getDetailedTripsByDriver(2);

        assertEquals(expectedDetailedTripDtos, result);
    }

    @Test
    void getDetailedTripsByDepartureDateGreaterThanEqualAndFilters_noFilters_okay() {
        when(tripRepository
                .findByDepartureDateGreaterThanEqualAndDepartureCityAndArrivalCityAndRemainingSeats(null, null, null, 0))
                .thenReturn(trips);

        List<DetailedTripDto> result = tripService.getDetailedTripsByDepartureDateGreaterThanEqualAndFilters(null, null,
                null, null);

        assertEquals(detailedTripDtos, result);
    }

    @Test
    void getDetailedTripsByDepartureDateGreaterThanEqualAndFilters_someFilters_okay() {
        List<Trip> tripsToReturn = new ArrayList<>();
        tripsToReturn.add(trips.get(1));
        tripsToReturn.add(trips.get(3));
        tripsToReturn.add(trips.get(4));

        List<DetailedTripDto> expectedDetailedTripDtos = new ArrayList<>();
        expectedDetailedTripDtos.add(detailedTripDtos.get(1));
        expectedDetailedTripDtos.add(detailedTripDtos.get(3));
        expectedDetailedTripDtos.add(detailedTripDtos.get(4));

        when(tripRepository
                .findByDepartureDateGreaterThanEqualAndDepartureCityAndArrivalCityAndRemainingSeats(LocalDate.parse("2022" +
                        "-01-01"), "Plovdiv", null, 5))
                .thenReturn(tripsToReturn);

        List<DetailedTripDto> result =
                tripService.getDetailedTripsByDepartureDateGreaterThanEqualAndFilters(LocalDate.parse("2022-01-01"),
                        "Plovdiv", null, 5);

        assertEquals(expectedDetailedTripDtos, result);
    }

    @Test
    void getDetailedTripsByDepartureDateGreaterThanEqualAndFilters_allFilters_okay() {
        List<Trip> tripsToReturn = new ArrayList<>();
        tripsToReturn.add(trips.get(1));
        tripsToReturn.add(trips.get(3));
        tripsToReturn.add(trips.get(4));

        List<DetailedTripDto> expectedDetailedTripDtos = new ArrayList<>();
        expectedDetailedTripDtos.add(detailedTripDtos.get(1));
        expectedDetailedTripDtos.add(detailedTripDtos.get(3));
        expectedDetailedTripDtos.add(detailedTripDtos.get(4));

        when(tripRepository
                .findByDepartureDateGreaterThanEqualAndDepartureCityAndArrivalCityAndRemainingSeats(LocalDate.parse("2022" +
                        "-01-01"), "Plovdiv", "Sofia", 5))
                .thenReturn(tripsToReturn);

        List<DetailedTripDto> result =
                tripService.getDetailedTripsByDepartureDateGreaterThanEqualAndFilters(LocalDate.parse("2022-01-01")
                        , "Plovdiv", "Sofia"
                        , 5);

        assertEquals(expectedDetailedTripDtos, result);
    }

    @Test
    void addTrip_correctIds_okay() {
        TripRequestDto tripRequestDto = new TripRequestDto(LocalDateTime.parse("2021-07-13T21:00:00"), 1, 1, 1);
        Trip expectedTrip = new Trip(LocalDateTime.parse("2021-07-13T21:00:00"), routes.get(0), buses.get(0),
                drivers.get(0));
        Trip addedTrip = new Trip(1, LocalDateTime.parse("2021-07-13T21:00:00"), routes.get(0), buses.get(0),
                drivers.get(0));

        when(routeRepository.findById(1)).thenReturn(Optional.of(routes.get(0)));
        when(busRepository.findById(1)).thenReturn(Optional.of(buses.get(0)));
        when(driverRepository.findById(1)).thenReturn(Optional.of(drivers.get(0)));
        when(tripValidation.isDepartureDateBeforeArrivalDate(any(), any())).thenReturn(true);
        when(tripRepository.findByBus_BusID(1)).thenReturn(new ArrayList<>());
        when(tripRepository.findByDriver_DriverID(1)).thenReturn(new ArrayList<>());
        when(tripValidation.isFreeForNewTrip(any(), any(), anyList())).thenReturn(true);
        when(tripRepository.save(any())).thenReturn(addedTrip);

        tripService.addTrip(tripRequestDto);

        verify(tripRepository, times(1)).save(expectedTrip);
    }

    @Test()
    void addTrip_departureDateAfterArrivalDate_throwsInvalidUserInputException() {
        TripRequestDto tripRequestDto = new TripRequestDto(LocalDateTime.parse("2021-07-13T21:00:00"), 1, 1, 1);

        when(routeRepository.findById(1)).thenReturn(Optional.of(routes.get(0)));
        when(busRepository.findById(1)).thenReturn(Optional.of(buses.get(0)));
        when(driverRepository.findById(1)).thenReturn(Optional.of(drivers.get(0)));
        assertThrows(InvalidUserInputException.class, () -> {
            tripService.addTrip(tripRequestDto);
        });
    }

    @Test()
    void addTrip_unavailableBus_throwsUnavailableTransportException() {
        TripRequestDto tripRequestDto = new TripRequestDto(LocalDateTime.parse("2021-07-13T21:00:00"), 1, 1, 1);

        when(routeRepository.findById(1)).thenReturn(Optional.of(routes.get(0)));
        when(busRepository.findById(1)).thenReturn(Optional.of(buses.get(0)));
        when(driverRepository.findById(1)).thenReturn(Optional.of(drivers.get(0)));
        when(tripValidation.isDepartureDateBeforeArrivalDate(any(), any())).thenReturn(true);

        assertThrows(UnavailableTransportException.class, () -> {
            tripService.addTrip(tripRequestDto);
        });
    }

    @Test()
    void addTrip_unavailableDriver_throwsUnavailableTransportException() {
        List<Trip> busTrips = trips.subList(1, 2);
        TripRequestDto tripRequestDto = new TripRequestDto(LocalDateTime.parse("2021-07-13T21:00:00"), 1, 1, 1);

        when(routeRepository.findById(1)).thenReturn(Optional.of(routes.get(0)));
        when(busRepository.findById(1)).thenReturn(Optional.of(buses.get(0)));
        when(driverRepository.findById(1)).thenReturn(Optional.of(drivers.get(0)));
        when(tripRepository.findByBus_BusID(1)).thenReturn(busTrips);
        when(tripValidation.isDepartureDateBeforeArrivalDate(any(), any())).thenReturn(true);
        when(tripValidation.isFreeForNewTrip(LocalDateTime.parse("2021-07-13T21:00:00"), LocalDateTime.parse("2021-07-14T00:10:00"), busTrips)).thenReturn(true);

        assertThrows(UnavailableTransportException.class, () -> {
            tripService.addTrip(tripRequestDto);
        });
    }

    @Test
    void updateTrip_correctIds_okay() {
        TripRequestDto tripRequestDto = new TripRequestDto(LocalDateTime.parse("2021-07-13T21:00:00"), 1, 1, 1);
        Trip expectedTrip = new Trip(1, LocalDateTime.parse("2021-07-13T21:00:00"), routes.get(0), buses.get(0),
                drivers.get(0));

        when(routeRepository.findById(1)).thenReturn(Optional.of(routes.get(0)));
        when(busRepository.findById(1)).thenReturn(Optional.of(buses.get(0)));
        when(driverRepository.findById(1)).thenReturn(Optional.of(drivers.get(0)));
        when(tripRepository.findById(1)).thenReturn(Optional.of(trips.get(0)));
        when(tripValidation.isDepartureDateBeforeArrivalDate(any(), any())).thenReturn(true);
        when(tripRepository.findByBus_BusID(1)).thenReturn(new ArrayList<>());
        when(tripRepository.findByDriver_DriverID(1)).thenReturn(new ArrayList<>());
        when(tripValidation.isFreeForNewTrip(any(), any(), anyList())).thenReturn(true);
        when(tripRepository.findById(1)).thenReturn(Optional.of(trips.get(0)));
        when(tripValidation.isNewBusSeatCountEqualOrHigherThanSoldSeats(any(), any())).thenReturn(true);
        when(tripRepository.save(any())).thenReturn(expectedTrip);

        tripService.updateTrip(1, tripRequestDto);

        verify(tripRepository, times(1)).save(expectedTrip);
    }

    @Test
    void updateTrip_incorrectTripId_throwResourceNotFoundException() {
        TripRequestDto tripRequestDto = new TripRequestDto(LocalDateTime.parse("2021-07-13T21:00:00"), 1, 1, 1);

        when(routeRepository.findById(1)).thenReturn(Optional.of(routes.get(0)));
        when(busRepository.findById(1)).thenReturn(Optional.of(buses.get(0)));
        when(driverRepository.findById(1)).thenReturn(Optional.of(drivers.get(0)));
        when(tripRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceMissingException.class, () -> {
            tripService.updateTrip(1, tripRequestDto);
        });
    }

    @Test
    void updateTrip_departureDateAfterArrivalDate_throwInvalidUserInputException() {
        TripRequestDto tripRequestDto = new TripRequestDto(LocalDateTime.parse("2021-07-13T21:00:00"), 1, 1, 1);

        when(routeRepository.findById(1)).thenReturn(Optional.of(routes.get(0)));
        when(busRepository.findById(1)).thenReturn(Optional.of(buses.get(0)));
        when(driverRepository.findById(1)).thenReturn(Optional.of(drivers.get(0)));
        when(tripRepository.findById(1)).thenReturn(Optional.of(trips.get(0)));
        when(tripValidation.isDepartureDateBeforeArrivalDate(any(), any())).thenReturn(false);

        assertThrows(InvalidUserInputException.class, () -> {
            tripService.updateTrip(1, tripRequestDto);
        });
    }

    @Test
    void updateTrip_unavailableBus_throwUnavailableTransportException() {
        TripRequestDto tripRequestDto = new TripRequestDto(LocalDateTime.parse("2021-07-13T21:00:00"), 1, 1, 1);

        when(routeRepository.findById(1)).thenReturn(Optional.of(routes.get(0)));
        when(busRepository.findById(1)).thenReturn(Optional.of(buses.get(0)));
        when(driverRepository.findById(1)).thenReturn(Optional.of(drivers.get(0)));
        when(tripRepository.findById(1)).thenReturn(Optional.of(trips.get(0)));
        when(tripValidation.isDepartureDateBeforeArrivalDate(any(), any())).thenReturn(true);

        assertThrows(UnavailableTransportException.class, () -> {
            tripService.updateTrip(1, tripRequestDto);
        });
    }

    @Test
    void updateTrip_unavailableDriver_throwUnavailableTransportException() {
        List<Trip> busTrips = trips.subList(1, 2);
        TripRequestDto tripRequestDto = new TripRequestDto(LocalDateTime.parse("2021-07-13T21:00:00"), 1, 1, 1);

        when(routeRepository.findById(1)).thenReturn(Optional.of(routes.get(0)));
        when(busRepository.findById(1)).thenReturn(Optional.of(buses.get(0)));
        when(driverRepository.findById(1)).thenReturn(Optional.of(drivers.get(0)));
        when(tripRepository.findById(1)).thenReturn(Optional.of(trips.get(0)));
        when(tripRepository.findByBus_BusID(1)).thenReturn(busTrips);
        when(tripValidation.isDepartureDateBeforeArrivalDate(any(), any())).thenReturn(true);
        when(tripValidation.isFreeForNewTrip(LocalDateTime.parse("2021-07-13T21:00:00"), LocalDateTime.parse("2021-07-14T00:10:00"), busTrips)).thenReturn(true);

        assertThrows(UnavailableTransportException.class, () -> {
            tripService.updateTrip(1, tripRequestDto);
        });
    }

    @Test
    void updateTrip_notEnoughSeatsInBus_throwInvalidUserInputException() {
        TripRequestDto tripRequestDto = new TripRequestDto(LocalDateTime.parse("2021-07-13T21:00:00"), 1, 1, 1);
        Trip expectedTrip = new Trip(1, LocalDateTime.parse("2021-07-13T21:00:00"), routes.get(0), buses.get(0),
                drivers.get(0));

        when(routeRepository.findById(1)).thenReturn(Optional.of(routes.get(0)));
        when(busRepository.findById(1)).thenReturn(Optional.of(buses.get(0)));
        when(driverRepository.findById(1)).thenReturn(Optional.of(drivers.get(0)));
        when(tripRepository.findById(1)).thenReturn(Optional.of(trips.get(0)));
        when(tripValidation.isDepartureDateBeforeArrivalDate(any(), any())).thenReturn(true);
        when(tripRepository.findByBus_BusID(1)).thenReturn(new ArrayList<>());
        when(tripRepository.findByDriver_DriverID(1)).thenReturn(new ArrayList<>());
        when(tripValidation.isFreeForNewTrip(any(), any(), anyList())).thenReturn(true);
        when(tripRepository.findById(1)).thenReturn(Optional.of(trips.get(0)));
        when(tripValidation.isNewBusSeatCountEqualOrHigherThanSoldSeats(any(), any())).thenReturn(false);

        assertThrows(InvalidUserInputException.class, () -> {
            tripService.updateTrip(1, tripRequestDto);
        });
    }

    @Test
    void deleteTrip_existingId_okay() {
        when(tripRepository.existsById(3)).thenReturn(true);

        tripService.deleteTrip(3);

        verify(tripRepository, times(1)).deleteById(3);
    }

    @Test
    void deleteTrip_incorrectId_okay() {
        when(tripRepository.existsById(3)).thenReturn(false);

        assertThrows(ResourceMissingException.class, () -> {
            tripService.deleteTrip(3);
        });
    }
}
