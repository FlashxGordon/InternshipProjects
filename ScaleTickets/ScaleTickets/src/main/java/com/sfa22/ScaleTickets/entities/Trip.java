package com.sfa22.ScaleTickets.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id", updatable = false, nullable = false)
    private int tripId;
    @Column(name = "departure_date", nullable = false)
    private LocalDateTime departureDate;
    @Column(name = "arrival_date", nullable = false)
    private LocalDateTime arrivalDate;
    @Column(name = "remaining_seats", nullable = false)
    private int remainingSeats;

    @ManyToOne(optional = false)
    private Route route;
    @ManyToOne(optional = false)
    private Bus bus;
    @ManyToOne(optional = false)
    private Driver driver;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public Trip() {
    }

    public Trip(LocalDateTime departureDate, Route route, Bus bus, Driver driver) {
        this.departureDate = departureDate;
        this.arrivalDate = departureDate.plusMinutes(route.getTravelDuration().toMinutes());
        this.remainingSeats = bus.getBusCapacity();
        this.route = route;
        this.bus = bus;
        this.driver = driver;
    }

    public Trip(LocalDateTime departureDate, LocalDateTime arrivalDate, int remainingSeats, Route route, Bus bus, Driver driver) {
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.remainingSeats = remainingSeats;
        this.route = route;
        this.bus = bus;
        this.driver = driver;
    }

    public Trip(int tripId, LocalDateTime departureDate, Route route, Bus bus, Driver driver) {
        this.tripId = tripId;
        this.departureDate = departureDate;
        this.arrivalDate = departureDate.plusMinutes(route.getTravelDuration().toMinutes());
        this.remainingSeats = bus.getBusCapacity();
        this.route = route;
        this.bus = bus;
        this.driver = driver;
    }

    public Trip(int tripId, LocalDateTime departureDate, LocalDateTime arrivalDate, int remainingSeats, Route route, Bus bus, Driver driver) {
        this.tripId = tripId;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.remainingSeats = remainingSeats;
        this.route = route;
        this.bus = bus;
        this.driver = driver;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public int getRemainingSeats() {
        return remainingSeats;
    }

    public void setRemainingSeats(int remainingSeats) {
        this.remainingSeats = remainingSeats;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return tripId == trip.tripId
                && remainingSeats == trip.remainingSeats
                && departureDate.equals(trip.departureDate)
                && arrivalDate.equals(trip.arrivalDate)
                && route.equals(trip.route)
                && bus.equals(trip.bus)
                && driver.equals(trip.driver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId, departureDate, arrivalDate, remainingSeats, route, bus, driver);
    }


}
