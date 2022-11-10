package com.sfa22.ScaleTickets.security;

import com.sfa22.ScaleTickets.dtos.AdminCodeDto;
import com.sfa22.ScaleTickets.mappers.AdminCodeMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminRegistrationCodeServiceImpl implements AdminRegistrationCodeService {
    private final PasswordEncoder encoder;
    private final AdminRegistrationCodeRepository adminRegistrationCodeRepository;

    private final AdminCodeMapper mapper;

    public AdminRegistrationCodeServiceImpl(PasswordEncoder encoder, AdminRegistrationCodeRepository adminRegistrationCodeRepository, AdminCodeMapper mapper) {
        this.encoder = encoder;
        this.adminRegistrationCodeRepository = adminRegistrationCodeRepository;
        this.mapper = mapper;
    }

    @Override
    public int saveSecurityCode(AdminCodeDto code) {
        AdminRegistrationCode adminCode = mapper.mapAdminCodeDtoToAdminCode(code);

        adminCode.setCode(encoder.encode(adminCode.getCode()));

        adminRegistrationCodeRepository.save(adminCode);

        return adminCode.getId();
    }

    @Override
    public boolean deleteSecurityCode(int id) {
        adminRegistrationCodeRepository.deleteById(id);
        return true;
    }
}
