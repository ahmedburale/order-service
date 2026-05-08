package com.example.orderservice.service;

import com.example.orderservice.dto.CreateOrderRequest;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;

// Marks this class as a Spring-managed service bean
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    // Constructor injection - Spring automatically provides the repository
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Creates a new order with PENDING status and saves to database
    public Order createOrder(CreateOrderRequest request) {
        Order order = new Order(
                request.getCustomerName(),
                request.getProduct(),
                request.getQuantity(),
                "PENDING"
        );
        return orderRepository.save(order);
    }

    // Retrieves a single order or throws if not found
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    // Returns all orders in the database
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Updates an existing order's fields
    public Order updateOrder(Long id, CreateOrderRequest request) {
        Order order = getOrderById(id);
        order.setCustomerName(request.getCustomerName());
        order.setProduct(request.getProduct());
        order.setQuantity(request.getQuantity());
        return orderRepository.save(order);
    }

    // Deletes an order by ID (throws if not found)
    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }
}