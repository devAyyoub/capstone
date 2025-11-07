package com.example.account_service.controller;

import com.example.account_service.entities.Account;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private static final List<Account> ACCOUNTS = new ArrayList<>(Arrays.asList(
            new Account(1L, 1L, 1000.0),
            new Account(2L, 1L, 500.0),
            new Account(3L, 2L, 2000.0)
    ));

    @GetMapping
    public List<Account> getAllAccounts() {
        return ACCOUNTS;
    }

    @GetMapping("/client/{clientId}")
    public List<Account> getAccountsByClient(@PathVariable Long clientId) {
        return ACCOUNTS.stream()
                .filter(a -> a.getClientId().equals(clientId))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return ACCOUNTS.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return Optional.ofNullable(account)
                .map(a -> {
                    a.setId(ACCOUNTS.stream()
                            .mapToLong(Account::getId)
                            .max()
                            .orElse(0L) + 1);
                    ACCOUNTS.add(a);
                    return a;
                })
                .orElseThrow(() -> new RuntimeException("Invalid account data"));
    }


    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable Long id, @RequestBody Account updatedAccount) {
        Account existing = ACCOUNTS.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Account not found"));

        existing.setClientId(updatedAccount.getClientId());
        existing.setBalance(updatedAccount.getBalance());
        return existing;
    }

    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id) {
        boolean removed = ACCOUNTS.removeIf(a -> a.getId().equals(id));
        if (!removed) {
            throw new RuntimeException("Account not found");
        }
        return "Account deleted successfully";
    }
}
