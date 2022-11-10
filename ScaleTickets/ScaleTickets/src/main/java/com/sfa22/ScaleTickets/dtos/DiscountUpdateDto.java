package com.sfa22.ScaleTickets.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class DiscountUpdateDto {
    @NotNull(message = "Expiration date cannot be null")
    private LocalDate expirationDate;

    public DiscountUpdateDto() {
    }

    public DiscountUpdateDto(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
