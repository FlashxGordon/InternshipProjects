package com.sfa22.ScaleTickets.validations;

import com.sfa22.ScaleTickets.entities.Bus;
import com.sfa22.ScaleTickets.entities.Trip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TripValidationTest {
  @InjectMocks
  TripValidation tripValidation;
  List<Trip> trips = new ArrayList<>();
  LocalDateTime START_DATE_1, START_DATE_2, END_DATE_1, END_DATE_2;

  @BeforeEach
  public void setUp() {
    START_DATE_1 = LocalDateTime.of(2021, 1, 1, 8, 30);
    START_DATE_2 = LocalDateTime.of(2022, 1, 1, 9, 30);
    END_DATE_1 = LocalDateTime.of(2021, 1, 17, 9, 30);
    END_DATE_2 = LocalDateTime.of(2022, 1, 20, 9, 30);

    trips.add(new Trip(1, LocalDateTime.of(2022, 1, 1, 8, 30),
            LocalDateTime.of(2022, 1, 12, 18, 30), 30,
            null, null, null));
    trips.add(new Trip(1, LocalDateTime.of(2023, 1, 1, 8, 30),
            LocalDateTime.of(2023, 1, 15, 18, 30), 30,
            null, null, null));
  }

  @Test
  void isDepartureDateBeforeArrivalDate_departureDateBeforeArrivalDate_true() {
    boolean result = tripValidation.isDepartureDateBeforeArrivalDate(START_DATE_1, END_DATE_1);

    assertTrue(result);
  }

  @Test
  void isDepartureDateBeforeArrivalDate_departureDateAfterArrivalDate_false() {
    boolean result = tripValidation.isDepartureDateBeforeArrivalDate(END_DATE_1, START_DATE_1);

    assertFalse(result);
  }

  @Test
  void isFreeForNewTrip_doNotOverlap_true() {
    boolean result = tripValidation.isFreeForNewTrip(START_DATE_1, END_DATE_1, trips);

    assertTrue(result);
  }

  @Test
  void isFreeForNewTrip_doOverlap_false() {
    boolean result = tripValidation.isFreeForNewTrip(START_DATE_2, END_DATE_2, trips);

    assertFalse(result);
  }

  @Test
  void isNewBusSeatCountEqualOrHigherThanSoldSeats_doesHaveEnoughSeats_true() {
    trips.get(0).setBus(new Bus("BV1233CV", 35, 3));

    boolean result = tripValidation.isNewBusSeatCountEqualOrHigherThanSoldSeats(trips.get(0), new Bus("BV1233CC", 6, 4));

    assertTrue(result);
  }

  @Test
  void isNewBusSeatCountEqualOrHigherThanSoldSeats_doesHaveExactNumberOfSeats_true() {
    trips.get(0).setBus(new Bus("BV1233CV", 35, 3));

    boolean result = tripValidation.isNewBusSeatCountEqualOrHigherThanSoldSeats(trips.get(0), new Bus("BV1233CC", 5, 4));

    assertTrue(result);
  }

  @Test
  void isNewBusSeatCountEqualOrHigherThanSoldSeats_doesNotHaveEnoughSeats_false() {
    trips.get(0).setBus(new Bus("BV1233CV", 35, 3));

    boolean result = tripValidation.isNewBusSeatCountEqualOrHigherThanSoldSeats(trips.get(0), new Bus("BV1233CC", 4, 4));

    assertFalse(result);
  }
}