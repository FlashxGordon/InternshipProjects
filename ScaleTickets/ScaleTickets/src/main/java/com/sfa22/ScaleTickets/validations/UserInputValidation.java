package com.sfa22.ScaleTickets.validations;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserInputValidation {
    private final DiscountValidation discountValidation;

    public UserInputValidation(DiscountValidation discountValidation) {
        this.discountValidation = discountValidation;
    }

    public boolean checkUserInput (String clientName, String email, String phoneNumber, String discountCode) {

        if (!isUserNameValid(clientName)) {
            return false;
        }

        if (!isUserEmailValid(email)) {
            return false;
        }

        if (!isUserPhoneNumberValid(phoneNumber)) {
            return false;
        }

        if (!discountValidation.isValid(discountCode)) {
            return false;
        }
        return true;
    }

    public boolean isUserNameValid(String clientName) {

        String pattern = "^[\\p{L} .'-]+$";

        return Pattern.compile(pattern).matcher(clientName).matches();
    }


    public boolean isUserEmailValid(String email) {

        String pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|" +
                "(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

        return Pattern.compile(pattern).matcher(email).matches();
    }



    public boolean isUserPhoneNumberValid(String phoneNumber) {

        String pattern = "(\\+)(359)8[789]\\d\\d{3}\\d{3}";

        return Pattern.compile(pattern).matcher(phoneNumber).matches();
    }

}
