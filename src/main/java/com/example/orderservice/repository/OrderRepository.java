package com.example.orderservice.repository;

import com.example.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Spring Data JPA auto-implements CRUD methods (save, findById, findAll, delete)
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Custom query method: Spring generates SQL from the method name
    List<Order> findByStatus(String status);
    List<Order> findByCustomerName(String customerName);
}

