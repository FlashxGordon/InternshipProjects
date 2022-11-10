package com.sfa22.ScaleTickets.validations;

import com.sfa22.ScaleTickets.entities.Discount;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.regex.Pattern;

@Component
public class DiscountValidation {

    public boolean isValid(String code) {

        String pattern = "^[a-z]{4,13}\\d{2}$";

        return Pattern.compile(pattern).matcher(code).matches();

    }

    public boolean doesDiscountCodeExist(String code, List<Discount> allDiscounts) {

        return allDiscounts.stream().anyMatch(discount -> discount.getCode().equals(code));
    }

}
