package com.example.orderservice.service;

// Custom exception thrown when an order ID does not exist in the database
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}