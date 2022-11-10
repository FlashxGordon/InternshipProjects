package com.sfa22.ScaleTickets.services.implementations;

import com.sfa22.ScaleTickets.data.RouteRepository;
import com.sfa22.ScaleTickets.dtos.RouteDto;
import com.sfa22.ScaleTickets.entities.Route;
import com.sfa22.ScaleTickets.exceptions.InvalidUserInputException;
import com.sfa22.ScaleTickets.exceptions.ResourceMissingException;
import com.sfa22.ScaleTickets.mappers.RouteMapper;
import com.sfa22.ScaleTickets.services.interfaces.ApiService;
import com.sfa22.ScaleTickets.services.interfaces.RouteService;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class RouteServiceImpl implements RouteService {

  private final RouteRepository routeRepository;
  private final RouteMapper mapper;

  private final ApiService apiService;

  public RouteServiceImpl(RouteRepository routeRepository, RouteMapper mapper, ApiService apiService) {
    this.routeRepository = routeRepository;
    this.mapper = mapper;
    this.apiService = apiService;
  }

  @Override
  public List<RouteDto> getAllRoutes() {
    List<Route> routes = routeRepository.findAll();
    List<RouteDto> routeDtoList = mapper.ListOfRouteToRouteDto(routes);
    return routeDtoList;
  }

  @Override
  public List<RouteDto> getRouteByDepartureCity(String city) {
    List<Route> routes = routeRepository.findByDepartureCity(city);
    List<RouteDto> routeDtoList = mapper.ListOfRouteToRouteDto(routes);
    return routeDtoList;


  }

  @Override
  public List<RouteDto> getRouteByArrivalCity(String city) {
    List<Route> routes = routeRepository.findByArrivalCity(city);
    List<RouteDto> routeDtoList = mapper.ListOfRouteToRouteDto(routes);
    return routeDtoList;
  }

  @Override
  public List<RouteDto> getByTripPriceEquals(double price) {
    List<Route> routes = routeRepository.findByTripPriceEquals(price);
    List<RouteDto> routeDtoList = mapper.ListOfRouteToRouteDto(routes);
    return routeDtoList;
  }

  @Override
  public List<RouteDto> findByTripPriceLessThan(double price) {
    List<Route> routes = routeRepository.findByTripPriceLessThan(price);
    List<RouteDto> routeDtoList = mapper.ListOfRouteToRouteDto(routes);
    return routeDtoList;
  }

  @Override
  public List<RouteDto> findByTripPriceGreaterThan(double price) {
    List<Route> routes = routeRepository.findByTripPriceGreaterThan(price);
    List<RouteDto> routeDtoList = mapper.ListOfRouteToRouteDto(routes);
    return routeDtoList;
  }

  @Override
  public RouteDto findByDepartureCityAndArrivalCity(String departureCity, String arrivalCity) {
    Route route = routeRepository.findByDepartureCityAndArrivalCity(departureCity, arrivalCity)
            .orElseThrow(() -> new ResourceMissingException("Route with departure city: " + departureCity + " and " +
                    "arrival city: " + arrivalCity + " not found", "Route"));
    return mapper.mapRouteToRouteDto(route);
  }

  @Override
  public RouteDto findRouteById(int id) {
    Route route =
            routeRepository.findById(id).orElseThrow(() -> new ResourceMissingException("Route with id: " + id +
                    " not found", "Route"));
    return mapper.mapRouteToRouteDto(route);
  }

  /**
   * Method that calls the Matrix Api service and sets the route data for the locations
   * and price of the route by the provided user input.
   * The data for the distance and duration of the travel is being set by data
   * provided by the Matrix API which searches the data by the departure
   * and arrival city from the user input.
   *
   * @param routeDto
   * @return routeId from the newly created route
   * @throws IOException
   */

  @Override
  public int saveRoute(RouteDto routeDto) throws IOException {
    int[] array;
    try {
       array = apiService.getDistanceAndDurationTime(routeDto.getDepartureCity(), routeDto.getArrivalCity());
    } catch (JSONException e) {

      throw new InvalidUserInputException("Wrong departure/arrival city name");
    }
    double km = array[0] * 0.001;
    Duration duration = Duration.ofSeconds(array[1]);
    routeDto.setDistance(Math.round(km));
    routeDto.setTravelDuration(duration);
    Route savedRoute = routeRepository.save(mapper.mapRouteDtoToRoute(routeDto));
    return savedRoute.getRouteId();
  }

  @Override
  public boolean updateRoute(int id, RouteDto routeDto) throws IOException{
    if (!routeRepository.existsById(id)) {
      throw new ResourceMissingException("Route with id: " + id + " not found", "Route");
    }

    int[] arrayContainingDistanceAndDuration =
            apiService.getDistanceAndDurationTime(routeDto.getDepartureCity(), routeDto.getArrivalCity());

    Optional<Route> optionalRoute = routeRepository.findById(id);

    Route searchedRoute = optionalRoute.orElseThrow(() -> new ResourceMissingException("Route not Found", "Route"));

    setRouteNewValues(searchedRoute, routeDto, arrayContainingDistanceAndDuration);

    routeRepository.save(searchedRoute);

    return true;
  }

  private void setRouteNewValues(Route searchedRoute, RouteDto routeDto, int[] arrayContainingDistanceAndDuration) {

    searchedRoute.setDepartureCity(routeDto.getDepartureCity());

    searchedRoute.setArrivalCity(routeDto.getArrivalCity());

    searchedRoute.setTripPrice(routeDto.getTripPrice());

    searchedRoute.setDistance(getDistanceInKM(arrayContainingDistanceAndDuration[0]));

    searchedRoute.setTravelDuration(getDurationInSeconds(arrayContainingDistanceAndDuration[1]));
  }

  private Duration getDurationInSeconds(int duration) {

    return Duration.ofSeconds(duration);
  }

  private double getDistanceInKM(int distanceInMeters) {

    return Math.round(distanceInMeters * 0.001);
  }

  @Override
  public boolean deleteRoute(int id) {
    if(!routeRepository.existsById(id)) {
      throw new ResourceMissingException("Route with id: " + id + " not found", "Route");
    }
    routeRepository.deleteById(id);
    return true;
  }
}
