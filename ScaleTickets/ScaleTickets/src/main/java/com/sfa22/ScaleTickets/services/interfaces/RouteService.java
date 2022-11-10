package com.sfa22.ScaleTickets.services.interfaces;

import com.sfa22.ScaleTickets.dtos.RouteDto;

import java.io.IOException;
import java.util.List;

public interface RouteService {

    List<RouteDto> getAllRoutes();

    List<RouteDto> getRouteByDepartureCity(String city);

    List<RouteDto> getRouteByArrivalCity(String city);

    List<RouteDto> getByTripPriceEquals(double price);

    List<RouteDto> findByTripPriceLessThan(double price);

    List<RouteDto> findByTripPriceGreaterThan(double price);

    RouteDto findByDepartureCityAndArrivalCity(String departureCity, String arrivalCity);

    RouteDto findRouteById(int id);

    int saveRoute(RouteDto routeDto) throws IOException;

    boolean updateRoute(int id,RouteDto routeDto) throws IOException;

    boolean deleteRoute(int id);
}
