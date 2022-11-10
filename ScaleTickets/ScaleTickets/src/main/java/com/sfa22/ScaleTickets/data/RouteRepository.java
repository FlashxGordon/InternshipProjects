package com.sfa22.ScaleTickets.data;


import com.sfa22.ScaleTickets.entities.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    List<Route> findByDepartureCity(String departureCity);

    List<Route> findByArrivalCity(String arrivalCity);

    List<Route> findByTripPriceEquals(double tripPrice);

    List<Route> findByTripPriceLessThan(double tripPrice);

    List<Route> findByTripPriceGreaterThan(double tripPrice);

    Optional<Route> findByDepartureCityAndArrivalCity(String departureCity, String arrivalCity);

}
