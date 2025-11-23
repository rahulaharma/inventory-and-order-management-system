package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.Service.OrderItemService;
import org.example.Service.OrderService;
import org.example.model.Order;
import org.example.model.OrderItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")

public class OrderItemController {
    private final OrderItemService orderItemService;
    private final OrderService orderService;
    public OrderItemController(OrderItemService orderItemService,OrderService orderService){
        this.orderItemService=orderItemService;
        this.orderService=orderService;
    }
    @PostMapping
    public ResponseEntity<OrderItem> createItem(@RequestBody OrderItem item){
        OrderItem saved=orderItemService.saveOrderItem(item);
        return ResponseEntity.status(201).body(saved);
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getItemsByOrder(@PathVariable Long orderId){
        Order order=orderService.getOrderById(orderId)
                .orElseThrow(()->new EntityNotFoundException("Order not found for order id:"+orderId));
        List<OrderItem> items = orderItemService.getItemsByOrder(order);
        return ResponseEntity.ok(items);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id){
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
