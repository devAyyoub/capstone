package com.example.account_service.entities;

public class Account {
    private Long id;
    private Long clientId;
    private Double balance;

    public Account(Long id, Long clientId, Double balance) {
        this.id = id;
        this.clientId = clientId;
        this.balance = balance;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getClientId() {
        return clientId;
    }

    public Double getBalance() {
        return balance;
    }
}