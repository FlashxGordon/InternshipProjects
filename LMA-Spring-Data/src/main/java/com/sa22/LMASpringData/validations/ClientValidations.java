package com.sa22.LMASpringData.validations;

import com.sa22.LMASpringData.entities.ClientEntity;
import com.sa22.LMASpringData.repositories.ClientRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class ClientValidations {

    private final ClientRepository clientRepository;

    public ClientValidations(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public boolean isClientFound(long clientId) {

        Optional<Optional<ClientEntity>> clientEntity = Optional.of(clientRepository.findById(clientId));

        return clientEntity.get().isPresent();
    }

    public boolean isClientFound(String clientName) {

        Optional<Optional<ClientEntity>> clientEntity = Optional.of(clientRepository.findByClientName(clientName));

        return clientEntity.get().isPresent();
    }
}
