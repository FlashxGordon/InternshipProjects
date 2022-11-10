package com.sfa22.ScaleTickets.security;

import com.sfa22.ScaleTickets.security.requests.RegisterRequest;

public interface RegisterService {
    Long register(RegisterRequest request);
}
