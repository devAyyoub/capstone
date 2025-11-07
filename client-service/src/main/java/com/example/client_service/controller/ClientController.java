package com.example.client_service.controller;

import com.example.client_service.entities.Client;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private static final List<Client> CLIENTS = new ArrayList<>(Arrays.asList(
            new Client(1L, "Alice"),
            new Client(2L, "Bob")
    ));

    @GetMapping
    public List<Client> getAllClients() {
        return CLIENTS;
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable Long id) {
        return CLIENTS.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return Optional.ofNullable(client)
                .map(c -> {
                    c.setId(CLIENTS.stream()
                            .mapToLong(Client::getId)
                            .max()
                            .orElse(0L) + 1);
                    CLIENTS.add(c);
                    return c;
                })
                .orElseThrow(() -> new RuntimeException("Invalid client data"));
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        Client existing = CLIENTS.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Client not found"));

        existing.setName(updatedClient.getName());
        return existing;
    }

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable Long id) {
        boolean removed = CLIENTS.removeIf(c -> c.getId().equals(id));
        if (!removed) {
            throw new RuntimeException("Client not found");
        }
        return "Client deleted successfully";
    }
}
