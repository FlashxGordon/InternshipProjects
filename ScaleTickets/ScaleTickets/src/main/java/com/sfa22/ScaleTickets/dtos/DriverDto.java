package com.sfa22.ScaleTickets.dtos;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class DriverDto {

    @PositiveOrZero
    private int driverID;

    @NotBlank(message = "First Name cannot be Blank")
    @NotEmpty(message = "First Name cannot be Empty")
    @NotNull(message = "First Name cannot be null")
    @Size(max = 30, message = "Fist name must not be longer than 30 characters")
    private String firstName;

    @NotBlank(message = "Last Name cannot be Blank")
    @NotEmpty(message = "Last Name cannot be Empty")
    @NotNull(message = "Last Name cannot be null")
    @Size(max = 30, message = "Last name must not be longer than 30 characters")
    private String lastName;

    @NotBlank(message = "Phone Number cannot be Blank")
    @NotEmpty(message = "Phone Number cannot be Empty")
    @NotNull(message = "Phone Number cannot be null")
    @Size(max = 13, message = "Phone number must not be longer than 13 character")
    private String phoneNumber;

    @PositiveOrZero(message = "Daily wage must be positive or zero")
    private double dailyWage;

    public DriverDto() {

    }

    public DriverDto(int driverID, String firstName, String lastName, String phoneNumber, double dailyWage) {
        this.driverID = driverID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dailyWage = dailyWage;
    }

    public DriverDto(String firstName, String lastName, String phoneNumber, double dailyWage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dailyWage = dailyWage;
    }
}
