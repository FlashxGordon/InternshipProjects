package com.sa22.LMASpringData.services;

import com.sa22.LMASpringData.dtos.ClientDto;
import com.sa22.LMASpringData.entities.ClientEntity;
import com.sa22.LMASpringData.repositories.ClientRepository;
import com.sa22.LMASpringData.services.serviceinterfaces.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper mapper;

    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper mapper) {
        this.clientRepository = clientRepository;
        this.mapper = mapper;
    }

    @Override
    public List <ClientDto> getAllClients() {
        List<ClientEntity> clientEntityList = clientRepository.findAll();

        return clientEntityList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<ClientDto> getClientByName(String clientName) {
        ClientEntity clientEntity = clientRepository.findByClientName(clientName).orElseThrow(NoSuchElementException::new);
        return Optional.ofNullable(mapToDTO(clientEntity));
    }

    @Override
    public ClientDto getClientById(long id) {
        ClientEntity clientEntity = clientRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return mapToDTO(clientEntity);
    }


    @Override
    public ClientDto createClient(ClientDto clientDto) {

        ClientEntity clientEntity = mapToEntity(clientDto);
        ClientEntity newClientEntity = clientRepository.save(clientEntity);

        return mapToDTO(newClientEntity);
    }

    // convert Entity into DTO
    private ClientDto mapToDTO(ClientEntity clientEntity) {
        return mapper.map(clientEntity, ClientDto.class);
    }

    // convert DTO to entity
    private ClientEntity mapToEntity(ClientDto clientDto) {
        return mapper.map(clientDto, ClientEntity.class);
    }

}

