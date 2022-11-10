package com.sfa22.ScaleTickets.security;

import com.sfa22.ScaleTickets.exceptions.InvalidCredentialsException;
import com.sfa22.ScaleTickets.security.requests.RegisterRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final AdminRegistrationCodeRepository adminRegistrationCodeRepository;



    public RegisterServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                               PasswordEncoder passwordEncoder, UserDetailsService userDetailsService,
                               AdminRegistrationCodeRepository adminRegistrationCodeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.adminRegistrationCodeRepository = adminRegistrationCodeRepository;
    }

    @Override
    public Long register(RegisterRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();
        String securityCode = request.getSecurityCode();


        userRepository.findByUsername(username).ifPresent(e -> {
            throw new IllegalArgumentException("Username already exists!");
        });


        String pass = passwordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(pass);
        user.setEmail(email);
        Role userRole = null;
        if (checkValidSecurityCode(securityCode)) {
            userRole = roleRepository.findRoleByAuthority("ADMIN")
                    .orElse(new Role("ADMIN"));
        } else {
            userRole = roleRepository.findRoleByAuthority("USER")
                    .orElse(new Role("USER"));
        }
        user.setRoles(Collections.singleton(userRole));

        User createdUser = userRepository.save(user);

        login(username, password);
        return createdUser.getId();


    }

    private boolean checkValidSecurityCode(String securityCode) {
        List<AdminRegistrationCode> codes = adminRegistrationCodeRepository.findAll();
        for (AdminRegistrationCode c : codes) {
            if (passwordEncoder.matches(securityCode, c.getCode()))
                return true;
        }
        return false;
    }

    private boolean login(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new InvalidCredentialsException("Invalid password. Please try again.");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return true;



    }
}
