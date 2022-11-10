package com.sfa22.ScaleTickets.security;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authority;

    public Role() {
    }

    public Role(String authority) {
        this.authority = authority;
    }


    @Override
    public String getAuthority() {
        return authority;
    }

    
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}

