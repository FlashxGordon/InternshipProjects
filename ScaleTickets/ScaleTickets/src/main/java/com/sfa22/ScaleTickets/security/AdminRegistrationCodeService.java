package com.sfa22.ScaleTickets.security;


import com.sfa22.ScaleTickets.dtos.AdminCodeDto;

public interface AdminRegistrationCodeService {

    int saveSecurityCode(AdminCodeDto code);

    boolean deleteSecurityCode(int id);
}
