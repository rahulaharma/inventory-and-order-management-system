package org.example.repository;

import org.example.model.Order;
import org.example.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findByOrder(Order order);
}
