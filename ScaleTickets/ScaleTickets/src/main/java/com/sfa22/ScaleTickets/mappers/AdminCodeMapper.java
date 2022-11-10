package com.sfa22.ScaleTickets.mappers;

import com.sfa22.ScaleTickets.dtos.AdminCodeDto;
import com.sfa22.ScaleTickets.security.AdminRegistrationCode;
import org.springframework.stereotype.Component;

@Component
public class AdminCodeMapper {

    public AdminRegistrationCode mapAdminCodeDtoToAdminCode(AdminCodeDto code){
        return new AdminRegistrationCode(code.getCode());
    }
}
