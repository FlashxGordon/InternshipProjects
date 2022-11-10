package com.sa22.LMASpringData.controllers;

import com.sa22.LMASpringData.dtos.ClientDto;
import com.sa22.LMASpringData.services.serviceinterfaces.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/clients")
    public List<ClientDto> getAllClients() throws NoSuchElementException{
        return ResponseEntity.ok(clientService.getAllClients()).getBody();
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(value = "/clients", params = {"name"})
    public Optional<ClientDto> getAllClients(@RequestParam String name){
        return ResponseEntity.ok(clientService.getClientByName(name)).getBody();
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/clients/{id}")
    public ClientDto getAllClients(@PathVariable long id){
        return ResponseEntity.ok(clientService.getClientById(id)).getBody();
    }

}
