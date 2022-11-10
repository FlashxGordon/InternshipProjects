package com.sfa22.ScaleTickets.security;


import com.sfa22.ScaleTickets.security.requests.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@Validated
public class RegisterController {

    private final RegisterServiceImpl registrationService;

    public RegisterController(RegisterServiceImpl registerService) {
        this.registrationService = registerService;
    }


    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {

        long id = registrationService.register(request);

        return ResponseEntity.created(URI.create(String.valueOf(id))).build();
    }

}
