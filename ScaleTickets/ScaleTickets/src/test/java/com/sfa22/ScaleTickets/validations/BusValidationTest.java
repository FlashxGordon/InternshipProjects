package com.sfa22.ScaleTickets.validations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class BusValidationTest {

    @InjectMocks
    BusValidation busValidation;

    private String correctBusPlate;

    private String anotherCorrectBusPlate;

    private String incorrectBusPlate;

    @BeforeEach
    public void setUp() {

        correctBusPlate = "PB0000PB";

        anotherCorrectBusPlate = "A5555AA";

        incorrectBusPlate = "05PPPP05";
    }

    @Test
    void isBusPlateCorrect_isPlateCorrect_okay() {

        boolean result = busValidation.isBusPlateCorrect(correctBusPlate);

        assertTrue(result);
    }

    @Test
    void isBusPlateCorrect_isPlateWithOneStartLetter_okay() {

        boolean result = busValidation.isBusPlateCorrect(anotherCorrectBusPlate);

        assertTrue(result);
    }

    @Test
    void isBusPlateCorrect_isPlateIncorrect_okay() {

        boolean result = busValidation.isBusPlateCorrect(incorrectBusPlate);

        assertFalse(result);
    }
}