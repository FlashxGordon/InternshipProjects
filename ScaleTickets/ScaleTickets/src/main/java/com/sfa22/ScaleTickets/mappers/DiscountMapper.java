package com.sfa22.ScaleTickets.mappers;

import com.sfa22.ScaleTickets.dtos.DiscountDto;
import com.sfa22.ScaleTickets.entities.Discount;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiscountMapper {

    public Discount mapDiscountDtoToDiscount(DiscountDto discountDto) {

        return new Discount(discountDto.getDiscountId(), discountDto.getPercentage(),
                discountDto.getCode(), discountDto.getExpirationDate());
    }

    public DiscountDto mapDiscountToDiscountDto(Discount discount) {

        return new DiscountDto(discount.getDiscountId(), discount.getPercentage(),
                discount.getCode(), discount.getExpirationDate());
    }

    public List<DiscountDto> ListOfDiscountToDiscountDto(List<Discount> listOfDiscounts) {

        return listOfDiscounts.stream().map(this::mapDiscountToDiscountDto).collect(Collectors.toList());
    }
}