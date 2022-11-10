package com.sfa22.ScaleTickets.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AdminCodeDto {

    @NotBlank(message = "Admin code cannot be Blank")
    @NotEmpty(message = "Admin code cannot be Empty")
    @NotNull(message = "Admin code cannot be null")
    @Size(min = 4, max = 15, message = "Admin code must be between 4 and 15 characters long")
    private String code;

    public AdminCodeDto() {
    }

    public AdminCodeDto(String code) {
        this.code = code;
    }
}
