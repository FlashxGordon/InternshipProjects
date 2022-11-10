package com.sfa22.ScaleTickets.security;

import com.sfa22.ScaleTickets.security.requests.LoginRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private final LoginServiceImpl loginService;

    public LoginController(LoginServiceImpl loginService) {
        this.loginService = loginService;
    }


    @PostMapping("/login")
    public void redirectToTrips(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        if (loginService.login(loginRequest)) {

            response.setStatus(302);
        }
    }
}