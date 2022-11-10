package com.sfa22.ScaleTickets.validations;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UserTicketValidationTest {

    @InjectMocks
    UserTicketValidation userTicketValidation;

    static String correctPhoneNumber;

    static String incorrectPhoneNumber;

    static String correctFullName;

    static String incorrectFullName;

    @BeforeAll
    public static void setUp() {

        correctFullName = "Patrick Brian";

        incorrectFullName = "PatrickBrian";

        correctPhoneNumber = "+359898243405";

        incorrectPhoneNumber = "+0000000000";
    }

    @Test
    void isUserNameValid_checkIfEnteredCodeIsValid_true() {
        boolean result = userTicketValidation.isUserNameValid(correctFullName);
        assertTrue(result);
    }

    @Test
    void isUserNameValid_checkIfEnteredCodeIsInvalid_true() {
        boolean result = userTicketValidation.isUserNameValid(incorrectFullName);
        assertFalse(result);
    }

    @Test
    void isDriverPhoneNumberValid_isNumberCorrect_okay() {

        boolean result = userTicketValidation.isUserPhoneNumberValid(correctPhoneNumber);

        assertTrue(result);
    }

    @Test
    void isDriverPhoneNumberValid_isNumberWrong_okay() {

        boolean result = userTicketValidation.isUserPhoneNumberValid(incorrectPhoneNumber);

        assertFalse(result);
    }
}
