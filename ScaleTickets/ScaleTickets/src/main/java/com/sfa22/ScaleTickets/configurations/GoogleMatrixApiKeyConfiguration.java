package com.sfa22.ScaleTickets.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleMatrixApiKeyConfiguration {

    @Value("${app.google.matrix.apiKey}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}