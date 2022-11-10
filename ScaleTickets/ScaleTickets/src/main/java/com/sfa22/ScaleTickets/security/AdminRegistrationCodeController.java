package com.sfa22.ScaleTickets.security;

import com.sfa22.ScaleTickets.dtos.AdminCodeDto;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@Validated
public class AdminRegistrationCodeController {
    private final AdminRegistrationCodeServiceImpl adminRegistrationCodeService;

    public AdminRegistrationCodeController(AdminRegistrationCodeServiceImpl adminRegistrationCodeService) {
        this.adminRegistrationCodeService = adminRegistrationCodeService;
    }

    @PostMapping(value = "/code")
    public ResponseEntity<Void> saveSecurityCode(@RequestBody AdminCodeDto code) {

        int id = adminRegistrationCodeService.saveSecurityCode(code);

        return ResponseEntity.created(URI.create("api/v1/code/" + id)).build();
    }

    @DeleteMapping(value = "/code", params = {"ID"})
    public ResponseEntity<Boolean> deleteSecurityCode(@RequestParam(name = "ID") int code) {

        return ResponseEntity.ok(adminRegistrationCodeService.deleteSecurityCode(code));
    }

}
