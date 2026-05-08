package com.example.orderservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// JPA entity mapped to the "orders" table in PostgreSQL
@Entity
@Table(name = "orders")
public class Order {

    // Auto-generated primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String product;

    @Column(nullable = false)
    private Integer quantity;

    // Order status tracks lifecycle (PENDING, SHIPPED, etc.)
    @Column(nullable = false)
    private String status;

    // Timestamp set automatically when order is first saved
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Required no-arg constructor for JPA
    protected Order() {}

    // Constructor for creating new orders in the service layer
    public Order(String customerName, String product, Integer quantity, String status) {
        this.customerName = customerName;
        this.product = product;
        this.quantity = quantity;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    // Lifecycle callback to set createdAt before first persist
    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    // Getters and setters for each field
    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}