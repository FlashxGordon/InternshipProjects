package com.sfa22.ScaleTickets.services.implementations;

import com.sfa22.ScaleTickets.configurations.GoogleMatrixApiKeyConfiguration;
import com.sfa22.ScaleTickets.services.interfaces.ApiService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class ApiServiceImpl implements ApiService {

    private final CloseableHttpClient httpClient;

    private final GoogleMatrixApiKeyConfiguration apiKeyConfiguration;

    public ApiServiceImpl(CloseableHttpClient httpClient, GoogleMatrixApiKeyConfiguration apiKeyConfiguration) {
        this.httpClient = httpClient;
        this.apiKeyConfiguration = apiKeyConfiguration;
    }

    @Override
    public int[] getDistanceAndDurationTime(String departureCity, String arrivalCity) throws IOException {

        int[] arrayWIthData = new int[2];

        JSONObject resultObject = getRequestResult(departureCity, arrivalCity);

        int distanceInMeters = getDistanceInMetersFromJsonObject(resultObject);

        int travelTimeInSeconds = getDurationInSecondsFromJsonObject(resultObject);

        arrayWIthData[0] = distanceInMeters;

        arrayWIthData[1] = travelTimeInSeconds;

        return arrayWIthData;
    }

    private JSONObject getRequestResult(String departureCity, String arrivalCity) throws IOException {

        HttpGet request = new HttpGet("https://maps.googleapis.com/maps/api/distancematrix/json?origins=" +
                departureCity + "&destinations=" + arrivalCity + "&units=metric&key=" + apiKeyConfiguration.getApiKey());

        JSONObject resultJson;

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();

            InputStream inputStream = entity.getContent();

            String result = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            resultJson = new JSONObject(result);
        }
        return resultJson;
    }

    private int getDistanceInMetersFromJsonObject(JSONObject resultJson) {

        int distanceInMeters = resultJson.getJSONArray("rows")
                .getJSONObject(0)
                .getJSONArray("elements")
                .getJSONObject(0).getJSONObject("distance").getInt("value");

        return distanceInMeters;
    }

    private int getDurationInSecondsFromJsonObject(JSONObject resultJson) {

        int travelTimeInSeconds = (resultJson.getJSONArray("rows")
                .getJSONObject(0)
                .getJSONArray("elements")
                .getJSONObject(0).getJSONObject("duration").getInt("value"));

        return travelTimeInSeconds;
    }
}