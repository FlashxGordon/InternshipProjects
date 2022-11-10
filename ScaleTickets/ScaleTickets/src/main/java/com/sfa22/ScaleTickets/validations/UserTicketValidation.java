package com.sfa22.ScaleTickets.validations;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserTicketValidation {

    public boolean checkUserInput(String clientName, String phoneNumber) {

        if (!isUserNameValid(clientName)) {
            return false;
        } else if (!isUserPhoneNumberValid(phoneNumber)) {
            return false;
        }

        return true;
    }

    public boolean isUserNameValid(String clientName) {
        String pattern = "^([A-Za-z]|'){1,30} ([A-Za-z]|'){1,30}$";
        return Pattern.compile(pattern).matcher(clientName).matches();

    }

    public boolean isUserPhoneNumberValid(String phoneNumber) {

        String pattern = "(\\+)(359)8[789]\\d\\d{3}\\d{3}";

        return Pattern.compile(pattern).matcher(phoneNumber).matches();
    }
}
