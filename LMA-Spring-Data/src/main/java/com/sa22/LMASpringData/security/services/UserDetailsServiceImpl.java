package com.sa22.LMASpringData.security.services;

import com.sa22.LMASpringData.security.entities.RoleEntity;
import com.sa22.LMASpringData.security.entities.UserEntity;
import com.sa22.LMASpringData.security.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserEntity userEntity = userRepository.findUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found" + username));
                return new
                        User(userEntity.getEmail(), userEntity.getPassword(), mapRolesToAuthorities(userEntity.getRoles()));

    }


    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<RoleEntity> roleEntities) {
        return roleEntities.stream().map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName())).collect(Collectors.toList());
    }

}

