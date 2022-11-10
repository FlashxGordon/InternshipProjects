package com.sfa22.ScaleTickets.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id", unique = true, updatable = false, insertable = false, nullable = false)
    private int driverID;

    @Column(name = "first_name", length = 30, unique = false, updatable = true, insertable = true, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 30, unique = false, updatable = true, insertable = true, nullable = false)
    private String lastName;

    @Column(name = "phone_number", length = 13, unique = true, updatable = true, insertable = true, nullable = false)
    private String phoneNumber;

    @Column(name = "daily_wage", unique = false, updatable = true, insertable = true, nullable = false)
    private double dailyWage;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    private List<Trip> trips;

    public Driver() {
    }

    public Driver(int driverID, String firstName, String lastName, String phoneNumber, double dailyWage) {
        this.driverID = driverID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dailyWage = dailyWage;
    }

    public Driver(String firstName, String lastName, String phoneNumber, double dailyWage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dailyWage = dailyWage;
    }

    public int getDriverID() {
        return driverID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getDailyWage() {
        return dailyWage;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDailyWage(double dailyWage) {
        this.dailyWage = dailyWage;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return driverID == driver.driverID && Double.compare(driver.dailyWage, dailyWage) == 0 &&
                firstName.equals(driver.firstName) && lastName.equals(driver.lastName) &&
                phoneNumber.equals(driver.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverID, firstName, lastName, phoneNumber, dailyWage);
    }

}