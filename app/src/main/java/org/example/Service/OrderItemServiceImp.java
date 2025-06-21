package org.example.Service;

import org.example.model.Order;
import org.example.model.OrderItem;
import org.example.repository.OrderItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImp implements OrderItemService{
    @Autowired
    private OrderItemRepo orderItemRepo;
    @Override
    public List<OrderItem> getItemsByOrder(Order order) {
        return orderItemRepo.findByOrder(order);
    }

    @Override
    public OrderItem saveOrderItem(OrderItem item) {
        return orderItemRepo.save(item);
    }

    @Override
    public void deleteOrderItem(Long itemId) {
        orderItemRepo.deleteById(itemId);
    }
}
