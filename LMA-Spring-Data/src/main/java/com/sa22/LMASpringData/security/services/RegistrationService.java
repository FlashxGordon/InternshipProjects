package com.sa22.LMASpringData.security.services;

import com.sa22.LMASpringData.security.dtos.RegisterDto;

public interface RegistrationService {

    long register(RegisterDto registerDto);
}
