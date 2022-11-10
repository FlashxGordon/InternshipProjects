package com.sfa22.ScaleTickets.validations;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class DiscountValidationTest {

    @InjectMocks
    DiscountValidation discountValidation;

    static String CODE, CODE2;


    @BeforeAll
    public static void setUp() {

        CODE = "summer12";
        CODE2 = "nonexistent";

    }


    @Test
    void isValid_checkIfEnteredCodeIsValid_true() {
        boolean result = discountValidation.isValid(CODE);
        assertTrue(result);
    }

    @Test
    void isValid_checkIfEnteredCodeIsInvalid_true() {
        boolean result = discountValidation.isValid(CODE2);
        assertFalse(result);
    }

}
