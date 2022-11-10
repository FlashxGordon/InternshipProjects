package com.sfa22.ScaleTickets.validations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class DriverValidationTest {

    @InjectMocks
    DriverValidation driverValidation;

    private String correctPhoneNumber;

    private String incorrectPhoneNumber;

    @BeforeEach
    public void setUp() {

        correctPhoneNumber = "+359891111111";

        incorrectPhoneNumber = "+0000000000";
    }

    @Test
    void isDriverPhoneNumberValid_isNumberCorrect_okay() {

        boolean result = driverValidation.isDriverPhoneNumberValid(correctPhoneNumber);

        assertTrue(result);
    }

    @Test
    void isDriverPhoneNumberValid_isNumberWrong_okay() {

        boolean result = driverValidation.isDriverPhoneNumberValid(incorrectPhoneNumber);

        assertFalse(result);
    }
}