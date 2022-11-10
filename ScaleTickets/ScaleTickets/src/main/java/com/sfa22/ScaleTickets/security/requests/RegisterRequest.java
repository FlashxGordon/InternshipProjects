package com.sfa22.ScaleTickets.security.requests;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class RegisterRequest {

    @NotBlank(message = "Username must not be blank")
    @Length(min = 6, max = 20, message = "Username must be between 6 and 20 characters")
    private String username;
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    private String email;
    @NotBlank(message = "Password must not be blank")
    @Length(min = 8, max = 50, message = "Password must be between 8 and 50 characters long")
    private String password;
    private String securityCode;


    public String getUsername() {
        return username;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
