package com.sa22.LMASpringData.repositories;

import com.sa22.LMASpringData.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByClientName(String clientName);

}
