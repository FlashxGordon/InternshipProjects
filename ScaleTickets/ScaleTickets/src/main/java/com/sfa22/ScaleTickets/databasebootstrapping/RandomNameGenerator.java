package com.sfa22.ScaleTickets.databasebootstrapping;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RandomNameGenerator {

    /**
     * Faker bean defined in order to have access to a large library of dummy data
     */

    @Bean
    public Faker faker() {
        return new Faker();
    }
}
