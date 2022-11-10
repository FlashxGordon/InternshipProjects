package com.sa22.LMASpringData.services.serviceinterfaces;

import com.sa22.LMASpringData.dtos.ClientDto;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<ClientDto> getAllClients();

    Optional<ClientDto> getClientByName(String clientName);

    ClientDto getClientById(long id);

    ClientDto createClient(ClientDto clientDto);


}
