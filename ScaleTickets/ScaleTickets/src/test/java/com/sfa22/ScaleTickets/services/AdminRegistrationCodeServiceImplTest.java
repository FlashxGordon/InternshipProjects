package com.sfa22.ScaleTickets.services;


import com.sfa22.ScaleTickets.dtos.AdminCodeDto;
import com.sfa22.ScaleTickets.mappers.AdminCodeMapper;
import com.sfa22.ScaleTickets.security.AdminRegistrationCode;
import com.sfa22.ScaleTickets.security.AdminRegistrationCodeRepository;
import com.sfa22.ScaleTickets.security.AdminRegistrationCodeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminRegistrationCodeServiceImplTest {

    @Spy
    PasswordEncoder passwordEncoder;

    @Spy
    AdminCodeMapper mapper;
    @Mock
    AdminRegistrationCodeRepository adminRegistrationCodeRepository;

    @InjectMocks
    AdminRegistrationCodeServiceImpl underTest;

    int ID;
    String CODE;
    AdminRegistrationCode code;


    AdminRegistrationCode encodedCode;
    AdminCodeDto dto;

    @BeforeEach
    public void setUp() {
        ID = 1;
        CODE = "ADMIN";
        dto = new AdminCodeDto(CODE);
        code = mapper.mapAdminCodeDtoToAdminCode(dto);
        encodedCode = new AdminRegistrationCode(ID, passwordEncoder.encode(CODE));


    }

    @Test
    void saveSecurityCode_doesItSaveCode_okay() {
        adminRegistrationCodeRepository.save(encodedCode);
        underTest.saveSecurityCode(dto);
        verify(adminRegistrationCodeRepository).save(encodedCode);


    }

    @Test
    void deleteSecurityCode_doesItDelete_okay() {
        adminRegistrationCodeRepository.deleteById(ID);
        boolean result = underTest.deleteSecurityCode(ID);
        assertTrue(result);

    }
}
