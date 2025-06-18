package org.example.Service;

import org.example.model.Order;
import org.example.model.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderItemService {
    public List<OrderItem> getItemsByOrder(Order order);
    public OrderItem saveOrderItem(OrderItem item);
    public void deleteOrderItem(Long itemId);


}
