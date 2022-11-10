package com.sa22.LMASpringData.security.services;

import com.sa22.LMASpringData.security.entities.RoleEntity;
import com.sa22.LMASpringData.security.repositories.RoleRepository;
import com.sa22.LMASpringData.security.entities.UserEntity;
import com.sa22.LMASpringData.security.repositories.UserRepository;
import com.sa22.LMASpringData.security.dtos.RegisterDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private String USER_ROLE = "ROLE_ADMIN";

    public RegistrationServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                                   UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public long register(RegisterDto registerDto) {
        String username = registerDto.getUsername();
        String email = registerDto.getEmail();
        String password = registerDto.getPassword();


        userRepository.findUserByUsername(username).ifPresent(e -> {
            log.info("Failed registration attempt with existing username {}", username);
            throw new IllegalArgumentException("Username already exists");
        });

        String encodedPassword = passwordEncoder.encode(password);

        RoleEntity roleEntity = roleRepository.findByName(USER_ROLE).orElse(new RoleEntity(USER_ROLE));

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRoles(Collections.singleton(roleEntity));

        UserEntity createdUser = userRepository.save(user);
        log.info("User successfully registered with username {}", username);

        login(username);
        return createdUser.getId();
    }

    private void login(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("User {} successfully logged in", username);
    }
}