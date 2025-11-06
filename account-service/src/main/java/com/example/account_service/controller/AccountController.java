package com.example.account_service.controller;

import com.example.account_service.entities.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccountController {
    private static final List<Account> ACCOUNTS = Arrays.asList(
            new Account(1L, 1L, 1000.0),
            new Account(2L, 1L, 500.0),
            new Account(3L, 2L, 2000.0)
    );

    @GetMapping("/accounts/client/{clientId}")
    public List<Account> getAccountsByClient(@PathVariable Long clientId) {
        return ACCOUNTS.stream()
                .filter(a -> a.getClientId().equals(clientId))
                .collect(Collectors.toList());
    }

    @GetMapping("/accounts/{id}")
    public Account getAccount(@PathVariable Long id) {
        return ACCOUNTS.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
}