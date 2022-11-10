package com.sfa22.ScaleTickets.mappers;

import com.sfa22.ScaleTickets.dtos.RouteDto;
import com.sfa22.ScaleTickets.entities.Route;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RouteMapper {

    public RouteDto mapRouteToRouteDto(Route route) {
        return new RouteDto(route.getRouteId(), route.getDepartureCity(), route.getArrivalCity(),
                route.getTripPrice(), route.getTravelDuration(),
                route.getDistance());

    }

    public Route mapRouteDtoToRoute(RouteDto routeDto) {
        return new Route(routeDto.getDepartureCity(),
                routeDto.getArrivalCity(), routeDto.getTripPrice(),
                routeDto.getTravelDuration(),
                routeDto.getDistance());
    }

    public List<RouteDto> ListOfRouteToRouteDto(List<Route> routes) {

        return routes.stream().map(this::mapRouteToRouteDto).collect(Collectors.toList());
    }
}
