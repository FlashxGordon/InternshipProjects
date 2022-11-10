package com.sfa22.ScaleTickets.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfa22.ScaleTickets.security.requests.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RegisterController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;


    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private RegisterServiceImpl registerService;

    static String userName;
    static String email;
    static String password;
    static String securityCode;
    static long userID;

    static RegisterRequest registerRequest;

    @BeforeEach
    public void setUp() {
        userName = "user_name";
        email = "email@scaletickets.em";
        password = "test1324";
        securityCode = null;
    }

    @Test
    void register_correctRegistration_statusCreated() throws Exception {
        when(registerService.register(any())).thenReturn(userID);

        Map<String, Object> body = new HashMap<>();
        body.put("username", userName);
        body.put("email", email);
        body.put("password", password);
        body.put("securityCode", securityCode);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

}
