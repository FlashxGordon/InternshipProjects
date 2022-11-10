package com.sfa22.ScaleTickets.security;

import com.sfa22.ScaleTickets.security.requests.LoginRequest;

public interface LoginService {
    boolean login(LoginRequest loginRequest);
}
