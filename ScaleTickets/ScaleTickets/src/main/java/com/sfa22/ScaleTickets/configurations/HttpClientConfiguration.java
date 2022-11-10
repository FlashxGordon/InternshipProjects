package com.sfa22.ScaleTickets.configurations;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class HttpClientConfiguration {

    @Bean
    public CloseableHttpClient closeableHttpClient() {
        return HttpClients.createDefault();
    }
}