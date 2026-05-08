package com.example.orderservice.controller;

import com.example.orderservice.dto.CreateOrderRequest;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// REST controller handling all order-related HTTP requests
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Order management endpoints")
public class OrderController {

    private final OrderService orderService;

    // Constructor injection of the service layer
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // POST /api/orders - create a new order
    @PostMapping
    @Operation(summary = "Create a new order")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    // GET /api/orders/{id} - retrieve a single order
    @GetMapping("/{id}")
    @Operation(summary = "Get an order by ID")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    // GET /api/orders - retrieve all orders
    @GetMapping
    @Operation(summary = "Get all orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // PUT /api/orders/{id} - update an existing order
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing order")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @Valid @RequestBody CreateOrderRequest request) {
        Order order = orderService.updateOrder(id, request);
        return ResponseEntity.ok(order);
    }

    // DELETE /api/orders/{id} - remove an order
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}