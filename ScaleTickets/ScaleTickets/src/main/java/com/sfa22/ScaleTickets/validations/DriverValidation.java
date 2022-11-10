package com.sfa22.ScaleTickets.validations;


import org.springframework.stereotype.Component;


import java.util.regex.Pattern;

@Component
public class DriverValidation {

    public boolean isDriverPhoneNumberValid(String phoneNumber) {

        String pattern = "(\\+)(359)8[789]\\d\\d{3}\\d{3}";

        return Pattern.compile(pattern).matcher(phoneNumber).matches();
    }
}