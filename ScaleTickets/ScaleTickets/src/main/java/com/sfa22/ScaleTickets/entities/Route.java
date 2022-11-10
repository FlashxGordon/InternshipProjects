package com.sfa22.ScaleTickets.entities;


import com.sfa22.ScaleTickets.dtos.RouteDto;

import javax.persistence.*;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "route")

public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id", updatable = false, nullable = false)
    private int routeId;
    @Column(name = "departure_city", length = 40)
    private String departureCity;
    @Column(name = "arrival_city", length = 40)
    private String arrivalCity;
    @Column(name = "trip_price")
    private double tripPrice;

    @Column(name = "travel_duration")
    private Duration travelDuration;
    @Column(name = "distance")
    private double distance;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<Trip> trips;

    public Route() {
    }

    ;

    public Route(int routeId, String departureCity, String arrivalCity, double tripPrice, Duration travelDuration, double distance) {
        this.routeId = routeId;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.tripPrice = tripPrice;
        this.travelDuration = travelDuration;
        this.distance = distance;
    }

    public Route(String departureCity, String arrivalCity, double tripPrice, Duration travelDuration, double distance) {
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.tripPrice = tripPrice;
        this.travelDuration = travelDuration;
        this.distance = distance;
    }

    public Route(Optional<RouteDto> routeById) {
    }

    public int getRouteId() {
        return routeId;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public double getTripPrice() {
        return tripPrice;
    }

    public Duration getTravelDuration() {
        return travelDuration;
    }


    public double getDistance() {
        return distance;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public void setTripPrice(double tripPrice) {
        this.tripPrice = tripPrice;
    }

    public void setTravelDuration(Duration travelDuration) {
        this.travelDuration = travelDuration;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route = (Route) o;
        return getRouteId() == route.getRouteId() &&
                Double.compare(route.getTripPrice(), getTripPrice()) == 0 &&
                Double.compare(route.getDistance(), getDistance()) == 0 &&
                Objects.equals(getArrivalCity(), route.getArrivalCity()) &&
                Objects.equals(getDepartureCity(), route.getDepartureCity()) &&
                Objects.equals(getTravelDuration(), route.getTravelDuration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRouteId(), getDepartureCity(), getArrivalCity(), getTripPrice(),
                getTravelDuration(), getDistance());
    }

}
