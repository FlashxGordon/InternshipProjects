package com.sfa22.ScaleTickets.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfa22.ScaleTickets.security.requests.LoginRequest;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = LoginController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private LoginServiceImpl loginServiceImpl;

    LoginRequest loginRequest = new LoginRequest();

    @BeforeEach
    public void setUp() {
        loginRequest.setUsername("user");
        loginRequest.setPassword("1324");
    }

    @Test
    void redirectToTrips_correctUsernameAndPassword_statusRedirected() throws Exception {
        when(loginServiceImpl.login(any())).thenReturn(true);
        mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().is3xxRedirection());
    }
}