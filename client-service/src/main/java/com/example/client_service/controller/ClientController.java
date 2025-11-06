package com.example.client_service.controller;

import com.example.client_service.entities.Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ClientController {
    private static final List<Client> CLIENTS = Arrays.asList(
            new Client(1L, "Alice"),
            new Client(2L, "Bob")
    );

    @GetMapping("/clients/{id}")
    public Client getClient(@PathVariable Long id) {
        return CLIENTS.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @GetMapping("/clients")
    public List<Client> getAllClients() {
        return CLIENTS;
    }
}