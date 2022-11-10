package com.sfa22.ScaleTickets.validations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class DailyRevenueValidationTest {

    @InjectMocks
    DailyRevenueValidation dailyRevenueValidation;

    LocalDate LOCAL_DATE_1, LOCAL_DATE_2;

    @BeforeEach
    void setUp() {

        LOCAL_DATE_1 = LocalDate.of(2022, 10, 1);
        LOCAL_DATE_2 = LocalDate.of(2022, 10, 2);
    }


    @Test
    void isDateRangeInputInCorrectOrder_ok() {

        assertTrue(dailyRevenueValidation.isDateRangeInputInCorrectOrder(LOCAL_DATE_1, LOCAL_DATE_2));
    }
}