package com.sfa22.ScaleTickets.security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRegistrationCodeRepository extends JpaRepository<AdminRegistrationCode,Integer> {
    Optional<AdminRegistrationCode> findByCode(String code);
}
