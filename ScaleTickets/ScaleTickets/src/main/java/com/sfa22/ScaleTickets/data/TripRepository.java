package com.sfa22.ScaleTickets.data;

import com.sfa22.ScaleTickets.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
    @Query("select t from Trip t where cast(t.departureDate as LocalDate) = ?1")
    List<Trip> findByDepartureDate(LocalDate departureDate);

    @Query("select t from Trip t where cast(t.departureDate as LocalDate) >= ?1")
    List<Trip> findByDepartureDateGreaterThanEqual(LocalDate departureDate);

    @Query("select t from Trip t where cast(t.departureDate as LocalDate) < ?1")
    List<Trip> findByDepartureDateLessThan(LocalDate departureDate);

    List<Trip> findByRoute_RouteId(int routeId);

    List<Trip> findByBus_BusID(int busID);

    List<Trip> findByDriver_DriverID(int driverID);

    List<Trip> findByRoute_DepartureCityAndRoute_ArrivalCity(String departureCity, String arrivalCity);

    @Query("select t from Trip t " +
            "where (cast(:departureDate as date) is null or cast(t.departureDate as LocalDate)= :departureDate) " +
            "and (:departureCity is null or t.route.departureCity = :departureCity) " +
            "and (:arrivalCity is null or t.route.arrivalCity = :arrivalCity) " +
            "and t.remainingSeats >= :remainingSeats")
    List<Trip> findByDepartureDateAndDepartureCityAndArrivalCityAndRemainingSeats(
            @Nullable @Param("departureDate") LocalDate departureDate,
            @Nullable @Param("departureCity") String departureCity,
            @Nullable @Param("arrivalCity") String arrivalCity, @Param("remainingSeats") int remainingSeats);

    @Query("select t from Trip t " +
            "where (cast(:departureDate as date) is null or cast(t.departureDate as LocalDate)>= :departureDate) " +
            "and (:departureCity is null or t.route.departureCity = :departureCity) " +
            "and (:arrivalCity is null or t.route.arrivalCity = :arrivalCity) " +
            "and t.remainingSeats >= :remainingSeats")
    List<Trip> findByDepartureDateGreaterThanEqualAndDepartureCityAndArrivalCityAndRemainingSeats(
            @Nullable @Param("departureDate") LocalDate departureDate,
            @Nullable @Param("departureCity") String departureCity,
            @Nullable @Param("arrivalCity") String arrivalCity, @Param("remainingSeats") int remainingSeats);
}