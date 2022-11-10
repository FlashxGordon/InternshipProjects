package com.sfa22.ScaleTickets.services.interfaces;

import java.io.IOException;

public interface ApiService {

    int[] getDistanceAndDurationTime(String departureCity, String arrivalCity) throws IOException;
}
