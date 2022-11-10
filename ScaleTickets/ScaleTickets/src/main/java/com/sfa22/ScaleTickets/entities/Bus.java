package com.sfa22.ScaleTickets.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "buses")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id", unique = true, updatable = false, insertable = false, nullable = false)
    private int busID;

    @Column(name = "bus_plate", length = 8, unique = true, updatable = true, insertable = true, nullable = false)
    private String busPlate;

    @Column(name = "bus_capacity", unique = false, updatable = true, insertable = true, nullable = false)
    private int busCapacity;

    @Column(name = "daily_cost", unique = false, updatable = true, insertable = true, nullable = false)
    private double dailyCost;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL)
    private List<Trip> trips;

    public Bus() {
    }

    public Bus(int busID, String busPlate, int busCapacity, double dailyCost) {
        this.busID = busID;
        this.busPlate = busPlate;
        this.busCapacity = busCapacity;
        this.dailyCost = dailyCost;
    }

    public Bus(String busPlate, int busCapacity, double dailyCost) {
        this.busPlate = busPlate;
        this.busCapacity = busCapacity;
        this.dailyCost = dailyCost;
    }

    public int getBusID() {
        return busID;
    }

    public String getBusPlate() {
        return busPlate;
    }

    public int getBusCapacity() {
        return busCapacity;
    }

    public double getDailyCost() {
        return dailyCost;
    }

    public void setBusPlate(String busPlate) {
        this.busPlate = busPlate;
    }

    public void setBusCapacity(int busCapacity) {
        this.busCapacity = busCapacity;
    }

    public void setDailyCost(double dailyCost) {
        this.dailyCost = dailyCost;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bus bus = (Bus) o;
        return busID == bus.busID && busCapacity == bus.busCapacity &&
                Double.compare(bus.dailyCost, dailyCost) == 0 &&
                busPlate.equals(bus.busPlate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(busID, busPlate, busCapacity, dailyCost);
    }

}