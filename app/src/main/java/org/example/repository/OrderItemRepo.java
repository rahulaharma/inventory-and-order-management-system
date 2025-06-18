package org.example.repository;

import org.example.model.Order;
import org.example.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findByOrder(Order order);
}
