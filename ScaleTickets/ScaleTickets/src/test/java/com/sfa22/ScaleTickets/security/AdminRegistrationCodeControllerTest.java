package com.sfa22.ScaleTickets.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.sfa22.ScaleTickets.dtos.AdminCodeDto;
import com.sfa22.ScaleTickets.mappers.AdminCodeMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AdminRegistrationCodeController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class AdminRegistrationCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminRegistrationCodeServiceImpl adminRegistrationCodeService;

    @SpyBean
    AdminCodeMapper mapper;

    @MockBean
    private PasswordEncoder encoder;

    private ObjectMapper objectMapper =
            new ObjectMapper().registerModule(new JavaTimeModule()).setDateFormat(new SimpleDateFormat());

    static int ID;

    static String code;

    static AdminRegistrationCode rawCode;
    static AdminCodeDto adminCodeDto;

    static AdminRegistrationCode encodedCode;

    @BeforeEach
    public void setUp() {

        ID = 1;

        code = "code1";

        adminCodeDto = new AdminCodeDto(code);

        rawCode = mapper.mapAdminCodeDtoToAdminCode(adminCodeDto);


    }

    @Test
    void saveSecurityCode_isStatusOk_okay() throws Exception {
        when(adminRegistrationCodeService.saveSecurityCode(adminCodeDto))
                .thenReturn(ID);

        Map<String, Object> body = new HashMap<>();
        body.put("code", code);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/code")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


    }

    @Test
    void deleteSecurityCode_isStatusOk_okay() throws Exception {

        mockMvc.perform(delete("/api/v1/code")
                        .param("ID", String.valueOf(ID)))
                .andExpect(status().isOk());
    }

}
