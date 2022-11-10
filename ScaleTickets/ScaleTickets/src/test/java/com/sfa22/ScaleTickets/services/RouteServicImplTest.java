package com.sfa22.ScaleTickets.services;

import com.sfa22.ScaleTickets.data.RouteRepository;
import com.sfa22.ScaleTickets.dtos.RouteDto;
import com.sfa22.ScaleTickets.entities.Route;
import com.sfa22.ScaleTickets.exceptions.InvalidUserInputException;
import com.sfa22.ScaleTickets.exceptions.ResourceMissingException;
import com.sfa22.ScaleTickets.mappers.RouteMapper;
import com.sfa22.ScaleTickets.services.implementations.ApiServiceImpl;
import com.sfa22.ScaleTickets.services.implementations.RouteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RouteServicImplTest {

    @Spy
    RouteMapper mapper;

    @Mock
    ApiServiceImpl apiServiceImpl;
    @Mock
    RouteRepository routeRepository;

    @InjectMocks
    RouteServiceImpl underTest;

    double PRICE;
    int DISTANCE;
    String ARRIVAL_CITY, DEPARTURE_CITY;
    Duration TRAVEL_DURATION;
    Route route;
    List<Route> routes;

    @BeforeEach
    public void setUp() {
        DEPARTURE_CITY = "CITY";
        ARRIVAL_CITY = "ARRIVALCITY";
        PRICE = 50.0;
        TRAVEL_DURATION = Duration.ofHours(33);
        DISTANCE = 5000;
        route = new Route(DEPARTURE_CITY, ARRIVAL_CITY,
                PRICE, TRAVEL_DURATION, DISTANCE);
        routes = new ArrayList<>();
        routes.add(route);


    }

    @Test
    void getAllRoutes_doesItReturnAllRoutes_okay() {

        when(routeRepository.findAll()).thenReturn(routes);

        List<RouteDto> routeDtoList = underTest.getAllRoutes();

        assertEquals(routes.size(), routeDtoList.size());

    }

    @Test
    void getRouteByDepartureCity_doesItReturnAllRoutes_okay() {
        when(routeRepository.findByDepartureCity(DEPARTURE_CITY)).thenReturn(routes);

        List<RouteDto> routeDtoList =
                underTest.getRouteByDepartureCity(DEPARTURE_CITY);

        assertEquals(routes.size(), routeDtoList.size());

    }


    @Test
    void getRouteByArrivalCity_doesItReturnAllRoutes_okay() {
        when(routeRepository.findByArrivalCity(ARRIVAL_CITY)).thenReturn(routes);

        List<RouteDto> routeDtoList =
                underTest.getRouteByArrivalCity(ARRIVAL_CITY);

        assertEquals(routes.size(), routeDtoList.size());

    }

    @Test
    void getByTripPriceEquals_doesItReturnRoutes_okay() {

        when(routeRepository.findByTripPriceEquals(PRICE)).thenReturn(routes);

        List<RouteDto> routeDtoList =
                underTest.getByTripPriceEquals(PRICE);

        assertEquals(routes.size(), routeDtoList.size());

    }

    @Test
    void findByTripPriceLessThan_doesItReturnRoutes_okay() {

        when(routeRepository.findByTripPriceLessThan(PRICE)).thenReturn(routes);

        List<RouteDto> routeDtoList =
                underTest.findByTripPriceLessThan(PRICE);

        assertEquals(routes.size(), routeDtoList.size());

    }


    @Test
    void findByDepartureCityAndArrivalCity_doesItReturnRoutes_okay() {

        when(routeRepository.findByDepartureCityAndArrivalCity(DEPARTURE_CITY, ARRIVAL_CITY))
                .thenReturn(Optional.ofNullable(route));

        RouteDto routeDto = underTest.findByDepartureCityAndArrivalCity(DEPARTURE_CITY,
                ARRIVAL_CITY);

        assertEquals(routeDto.getArrivalCity(), ARRIVAL_CITY);

        assertEquals(routeDto.getDepartureCity(), DEPARTURE_CITY);

    }

    @Test
    void findByTripPriceGreaterThan_doesItReturnRoutes_okay() {

        when(routeRepository.findByTripPriceGreaterThan(PRICE)).thenReturn(routes);

        List<RouteDto> routeDtoList =
                underTest.findByTripPriceGreaterThan(PRICE);

        assertEquals(routes.size(), routeDtoList.size());

    }

    @Test
    void saveRoute_doesItSaveRoute_okay() throws IOException {
        int[] array = new int[2];

        array[0] = (int) (DISTANCE / 0.001);

        array[1] = (int) TRAVEL_DURATION.toSeconds();

        when(apiServiceImpl.getDistanceAndDurationTime(DEPARTURE_CITY, ARRIVAL_CITY)).thenReturn(array);
        when(routeRepository.save(route)).thenReturn(route);

        underTest.saveRoute(mapper.mapRouteToRouteDto(route));

        verify(routeRepository).save(route);
    }

    @Test
    void saveRoute_apiServiceThrowsJSONException_throwInvalidUserInputException() throws IOException {

        when(apiServiceImpl.getDistanceAndDurationTime(any(), any())).thenAnswer(invocation -> {
            throw new org.json.JSONException("");
        });

        assertThrows(InvalidUserInputException.class, () -> underTest.saveRoute(mapper.mapRouteToRouteDto(route)));
    }

    @Test
    void updateRoute_doesItSaveRoute_okay() throws IOException {

        int[] array = new int[2];
        array[0] = (int) (DISTANCE / 0.001);
        array[1] = (int) TRAVEL_DURATION.toSeconds();

        when(routeRepository.existsById(1)).thenReturn(true);

        when(routeRepository.findById(any())).thenReturn(Optional.ofNullable(route));

        when(apiServiceImpl.getDistanceAndDurationTime(DEPARTURE_CITY, ARRIVAL_CITY)).thenReturn(array);

        when(routeRepository.save(route)).thenReturn(route);

        underTest.updateRoute(1, mapper.mapRouteToRouteDto(route));

        verify(routeRepository).save(route);
    }

    @Test
    void updateRoute_wrongId_throwResourceMissingException() {

        when(routeRepository.existsById(1)).thenReturn(false);

        assertThrows(ResourceMissingException.class, () -> underTest.updateRoute(1, mapper.mapRouteToRouteDto(route)));
    }

    @Test
    void findRouteById_doesItReturnRoute_okay() {

        when(routeRepository.findById(1)).thenReturn(Optional.ofNullable(route));

        RouteDto routeDto = underTest.findRouteById(1);

        assertEquals(routeDto.getDepartureCity(), route.getDepartureCity());

        assertEquals(routeDto.getArrivalCity(), route.getArrivalCity());


    }

    @Test
    void deleteRoute_doesItDeleteRoute_okay() {
        when(routeRepository.existsById(1)).thenReturn(true);

        routeRepository.deleteById(1);

        boolean result = underTest.deleteRoute(1);

        assertEquals(true, result);
    }

    @Test
    void deleteRoute_wrongId_throwResourceMissingException() {

        when(routeRepository.existsById(1)).thenReturn(false);

        routeRepository.deleteById(1);

        assertThrows(ResourceMissingException.class, () -> underTest.deleteRoute(1));
    }
}
