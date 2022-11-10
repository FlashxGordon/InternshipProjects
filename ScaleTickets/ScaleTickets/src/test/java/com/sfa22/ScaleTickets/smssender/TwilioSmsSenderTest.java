package com.sfa22.ScaleTickets.smssender;

import com.sfa22.ScaleTickets.dtos.DisplayTicketDto;
import com.sfa22.ScaleTickets.dtos.InputTicketDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class TwilioSmsSenderTest {

    @InjectMocks
    TwilioSmsSender twilioSmsSender;

    String CLIENT_NAME, EMAIL, PHONE_NUMBER,
            PHONE_NUMBER_INVALID, DISCOUNT, CLIENT_NAME_BLANK, MESSAGE;
    double COST, PRICE;

    int REMAINING_SEATS, CAPACITY;
    Integer TRIP_ID, TRIP_ID_NULL;
    InputTicketDto FALSE_DTO, CORRECT_DTO;
    String BUS_PLATE, FIRST_NAME, LAST_NAME, ARRIVAL_CITY, DEPARTURE_CITY;
    DisplayTicketDto DISPLAY_DTO;
    LocalDateTime DEPARTURE, ARRIVAL;

    @BeforeEach
    void setUp() {

        CLIENT_NAME = "Test Client";

        CLIENT_NAME_BLANK = "";

        EMAIL = "somemail@gmail.com";

        TRIP_ID = 1;

        TRIP_ID_NULL = null;

        PHONE_NUMBER = "+359899931201";

        PHONE_NUMBER_INVALID = "89993210541111";

        FIRST_NAME = "Test";

        LAST_NAME = "Client";

        DISCOUNT = "scale10";

        BUS_PLATE = "AB2325";

        PRICE = 50.0;

        CAPACITY = 10;

        MESSAGE = "Test message";

        REMAINING_SEATS = 10;

        COST = 100;

        DEPARTURE = LocalDateTime.now().plusHours(1);
        ARRIVAL = LocalDateTime.now().plusDays(1);

        CORRECT_DTO = new InputTicketDto(CLIENT_NAME, EMAIL, PHONE_NUMBER, TRIP_ID, DISCOUNT);

        FALSE_DTO = new InputTicketDto(CLIENT_NAME_BLANK, EMAIL, PHONE_NUMBER_INVALID, TRIP_ID, DISCOUNT);

        DISPLAY_DTO = new DisplayTicketDto(CLIENT_NAME, PRICE, BUS_PLATE,
                DEPARTURE_CITY, ARRIVAL_CITY, DEPARTURE, REMAINING_SEATS);


    }

    @Test
    void sendSms_isSmsSent_notOk() {

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                twilioSmsSender.sendSms(FALSE_DTO, DISPLAY_DTO));

        String expectedMessage = "Phone number [" + FALSE_DTO.getPhoneNumber() + "] is not a valid number";

        Assertions.assertEquals(expectedMessage, exception.getMessage());

    }


}