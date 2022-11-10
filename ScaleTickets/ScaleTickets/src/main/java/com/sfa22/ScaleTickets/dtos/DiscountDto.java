package com.sfa22.ScaleTickets.dtos;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class DiscountDto {

    @PositiveOrZero(message = "Discount id must be positive or zero")
    private int discountId;

    @Min(value = 10, message = "Discount percentage must be between 10 and 50")
    @Max(value = 50, message = "Discount percentage must be between 10 and 50")
    private int percentage;
    @NotBlank(message = "Discount code cannot be Blank")
    @NotEmpty(message = "Discount code cannot be Empty")
    @NotNull(message = "Discount code cannot be null")
    @Size(min = 4, max = 15, message = "Discount code must be between 4 and 15 characters long")
    private String code;

    @NotNull(message = "Expiration date cannot be null")
    private LocalDate expirationDate;

    public DiscountDto() {
    }

    public DiscountDto(int discountId, int percentage, String code, LocalDate expirationDate) {
        this.discountId = discountId;
        this.percentage = percentage;
        this.code = code;
        this.expirationDate = expirationDate;
    }

    public DiscountDto(int percentage, String code, LocalDate expirationDate) {
        this.percentage = percentage;
        this.code = code;
        this.expirationDate = expirationDate;
    }
}