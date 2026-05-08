package com.example.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// DTO that defines what fields a client must send when creating/updating an order
public class CreateOrderRequest {

    // Must not be null or empty/whitespace
    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Product is required")
    private String product;

    // Must be a non-null integer >= 1
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    // Getters and setters for JSON deserialization
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}