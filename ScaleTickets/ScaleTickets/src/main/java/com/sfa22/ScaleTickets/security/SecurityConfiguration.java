package com.sfa22.ScaleTickets.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
               // .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/register").permitAll()
                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/api/v1/trips", "/api/v1/trips/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/trips", "/api/v1/trips/**")
                .hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/api/v1/detailedTrips", "/api/v1/detailedTrips/**",
                        "/api/v1/routes", "/api/v1/routes/**", "/api/v1/drivers", "/api/v1/drivers/**",
                        "/api/v1/discounts", "/api/v1/discounts/**", "/api/v1/revenue", "/api/v1/revenue/**",
                        "/api/v1/grossProfit", "/api/v1/grossProfit/**", "/api/v1/tickets/**",
                        "/api/v1/expenses", "/api/v1/detailedTrips/**", "/api/v1/expenses/**",
                        "/api/v1/comparison")
                .hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/tickets", "/api/v1/buses",
                        "/api/v1/buses/**", "/api/v1/detailedTrips")
                .hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/tickets")
                .hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/api/v1/buses", "/api/v1/detailedTrips", "/api/v1/routes",
                        "/api/v1/drivers", "/api/v1/discounts", "/api/v1/code")
                .hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/v1/buses/**", "/api/v1/discounts",
                        "/api/v1/discounts/**")
                .hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/detailedTrips/**", "/api/v1/routes/**",
                        "/api/v1/override")
                .hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/buses/**", "/api/v1/detailedTrips/**",
                        "/api/v1/routes/**", "/api/v1/drivers/**", "/api/v1/code/**")
                .hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        return httpSecurity.build();
    }
}