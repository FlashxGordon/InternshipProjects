package com.sfa22.ScaleTickets.security;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserDetailsServiceImpl underTest;

    static String username;
    static User user;


    @BeforeEach
    public void setUp() {
        username = "angelafisher";
        user = new User();
        user.setUsername(username);


    }

    @Test
    void loadUserByUsername_checkIfItLoadsByUsername_okay() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.ofNullable(user));
        UserDetails result = underTest.loadUserByUsername(username);
        assertEquals(username, result.getUsername());
    }
}
