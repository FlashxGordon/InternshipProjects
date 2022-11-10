package com.sfa22.ScaleTickets.security;

import com.sfa22.ScaleTickets.exceptions.InvalidCredentialsException;
import com.sfa22.ScaleTickets.security.requests.LoginRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public LoginServiceImpl(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean login(LoginRequest loginRequest) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            throw new InvalidCredentialsException("Invalid password.Try again");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return true;


    }
}
