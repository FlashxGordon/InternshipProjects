package com.sfa22.ScaleTickets.services;

import com.sfa22.ScaleTickets.configurations.GoogleMatrixApiKeyConfiguration;
import com.sfa22.ScaleTickets.services.implementations.ApiServiceImpl;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApiServiceImplTest {

    @Mock
    CloseableHttpClient httpClient;

    @Mock
    CloseableHttpResponse response;

    @Mock
    GoogleMatrixApiKeyConfiguration apiKeyConfiguration;

    @Mock
    HttpEntity entity;

    @InjectMocks
    ApiServiceImpl apiService;

    private String arrivalCity;

    private String departureCity;

    private String responseAsString;

    private int durationInSeconds;

    private int distanceInMeters;

    private final byte arraySize = 2;

    private final int[] expectedArray = new int[arraySize];

    InputStream inputStream;

    @BeforeEach
    public void setup() throws IOException {

        arrivalCity = "Sofia";

        departureCity = "Burgas";

        durationInSeconds = 12811;

        distanceInMeters = 383142;

        expectedArray[0] = distanceInMeters;

        expectedArray[1] = durationInSeconds;

        responseAsString = "{\n" +
                "   \"destination_addresses\" : [ \"Burgas, Bulgaria\" ],\n" +
                "   \"origin_addresses\" : [ \"Sofia, Bulgaria\" ],\n" +
                "   \"rows\" : [\n" +
                "      {\n" +
                "         \"elements\" : [\n" +
                "            {\n" +
                "               \"distance\" : {\n" +
                "                  \"text\" : \"383 km\",\n" +
                "                  \"value\" : 383142\n" +
                "               },\n" +
                "               \"duration\" : {\n" +
                "                  \"text\" : \"3 hours 34 mins\",\n" +
                "                  \"value\" : 12811\n" +
                "               },\n" +
                "               \"status\" : \"OK\"\n" +
                "            }\n" +
                "         ]\n" +
                "      }\n" +
                "   ],\n" +
                "   \"status\" : \"OK\"\n" +
                "}\n";

        inputStream = new ByteArrayInputStream(responseAsString.getBytes());
    }

    @Test
    void getDistanceAndDurationTime_doesItReturnDistanceInMeters_okay() throws IOException {

        when(httpClient.execute(any())).thenReturn(response);

        when(response.getEntity()).thenReturn(entity);

        when(entity.getContent()).thenReturn(inputStream);

        int[] resultArray = apiService.getDistanceAndDurationTime(departureCity, arrivalCity);

        assertEquals(expectedArray[1], resultArray[1]);
    }

    @Test
    void getDistanceAndDurationTime_doesItReturnDurationInSeconds_okay() throws IOException {

        when(httpClient.execute(any())).thenReturn(response);

        when(response.getEntity()).thenReturn(entity);

        when(entity.getContent()).thenReturn(inputStream);

        int[] resultArray = apiService.getDistanceAndDurationTime(departureCity, arrivalCity);

        assertEquals(expectedArray[0], resultArray[0]);
    }
}