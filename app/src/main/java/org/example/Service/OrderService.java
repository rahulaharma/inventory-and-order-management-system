package org.example.Service;

import org.example.model.Order;
import java.util.List;
import java.util.Optional;
public interface OrderService {
    Order createOrder(Order order);
    List<Order> getAllOrders();
    Optional<Order> getOrderById(long id);
    List<Order> getOrdersBySalesPersonId(Long salesPersonId);
    List<Order> getOrdersByCustomerId(Long customerId);
    Order updateOrderStatus(Long orderId, String status);
    void deleteOrder(long id);
}
