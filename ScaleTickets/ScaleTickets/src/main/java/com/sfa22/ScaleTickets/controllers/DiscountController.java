package com.sfa22.ScaleTickets.controllers;

import com.sfa22.ScaleTickets.dtos.DiscountDto;
import com.sfa22.ScaleTickets.dtos.DiscountUpdateDto;
import com.sfa22.ScaleTickets.services.interfaces.DiscountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Validated
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping("/discounts/{id}")
    public ResponseEntity<DiscountDto> getDiscountById(@PathVariable int id) {

        return ResponseEntity.ok(discountService.getDiscountById(id));
    }


    @GetMapping(value = "/discounts")
    public ResponseEntity<List<DiscountDto>> getAllDiscounts() {

        return ResponseEntity.ok(discountService.getAllDiscounts());
    }


    @GetMapping(value = "/discounts", params = {"expirationDateAfterAndOn"})
    public ResponseEntity<List<DiscountDto>> getDiscountByExpirationDateAfterAndEqual(
            @RequestParam(name = "expirationDateAfterAndOn")
            @NotNull(message = "Expiration date cannot be null")
            LocalDate expirationDate) {

        return ResponseEntity.ok(discountService.getDiscountByExpirationDateAfterAndEqual(expirationDate));
    }


    @GetMapping(value = "/discounts", params = {"expirationDateBefore"})
    public ResponseEntity<List<DiscountDto>> getDiscountByExpirationDateBefore(
            @RequestParam(name = "expirationDateBefore")
            @NotNull(message = "Expiration date cannot be null")
            LocalDate expirationDate) {

        return ResponseEntity.ok(discountService.getDiscountByExpirationDateBefore(expirationDate));
    }


    @GetMapping(value = "/discounts", params = {"code"})
    public ResponseEntity<DiscountDto> getDiscountByCode(@RequestParam(name = "code")
                                                         @NotBlank(message = "Discount code cannot be Blank")
                                                         @NotEmpty(message = "Discount code cannot be Empty")
                                                         @NotNull(message = "Discount code cannot be null")
                                                         String discountCode) {

        return ResponseEntity.ok(discountService.getDiscountByCode(discountCode));
    }


    @GetMapping(value = "/discounts", params = {"code", "expirationDateAfterAndOn"})
    public ResponseEntity<DiscountDto> getDiscountByCodeAndExpirationDateAfterAndEqual
            (@RequestParam(name = "code")
             @NotBlank(message = "Discount code cannot be Blank")
             @NotEmpty(message = "Discount code cannot be Empty")
             @NotNull(message = "Discount code cannot be null")
             String discountCode,
             @RequestParam(name = "expirationDateAfterAndOn")
             @NotNull(message = "Expiration date cannot be null")
             LocalDate expirationDate) {

        return ResponseEntity.ok
                (discountService.getDiscountByCodeAndExpirationDateAfterAndEqual(discountCode, expirationDate));
    }


    @PostMapping(value = "/discounts")
    public ResponseEntity<Void> addNewDiscount(@Valid @RequestBody DiscountDto discountDto) {

        int newDiscountID = discountService.addNewDiscount(discountDto);

        return ResponseEntity.created(URI.create("api/v1/discounts/" + newDiscountID)).build();
    }


    @PatchMapping(value = "/discounts", params = {"code"})
    public void updateDiscountExpirationDateFoundByCode
            (@RequestParam(name = "code")
             @NotBlank(message = "Discount code cannot be Blank")
             @NotEmpty(message = "Discount code cannot be Empty")
             @NotNull(message = "Discount code cannot be null")
             String discountCode,
             @Valid
             @RequestBody
             DiscountUpdateDto discountDto) {

        discountService.updateDiscountExpirationDateFoundByCode(discountCode, discountDto);
    }
}