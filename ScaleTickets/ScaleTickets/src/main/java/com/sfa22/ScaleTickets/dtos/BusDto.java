package com.sfa22.ScaleTickets.dtos;


import lombok.Data;

import javax.validation.constraints.*;

@Data
public class BusDto {

    @PositiveOrZero
    private int busID;

    @NotBlank(message = "Plate cannot be Blank")
    @NotEmpty(message = "Plate cannot be Empty")
    @NotNull(message = "Plate cannot be null")
    @Size(min = 8, max = 8, message = "Bus plate must be 8 characters long")
    private String busPlate;

    @Min(value = 10, message = "Bus capacity must be greater than or equal to 10")
    @Max(value = 300, message = "Bus capacity must be less than or equal to 300")
    private int busCapacity;

    @PositiveOrZero
    private double dailyCost;

    public BusDto() {
    }

    public BusDto(int busID, String busPlate, int busCapacity, double dailyCost) {
        this.busID = busID;
        this.busPlate = busPlate;
        this.busCapacity = busCapacity;
        this.dailyCost = dailyCost;
    }

    public BusDto(String busPlate, int busCapacity, double dailyCost) {
        this.busPlate = busPlate;
        this.busCapacity = busCapacity;
        this.dailyCost = dailyCost;
    }
}