package org.example.controller;
import org.example.Service.OrderService;
import org.example.model.Order;
import org.example.model.OrderItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/orders")
@CrossOrigin(origins = "http://localhost:1234")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService){
        this.orderService=orderService;
    }
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        Order saved= orderService.createOrder(order);
        return ResponseEntity.status(201).body(saved);
    }
    @GetMapping
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable long id){
        return orderService.getOrderById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/sales/{salesId}")
    public ResponseEntity<List<Order>> getOrdersBySalesPerson(long salesId){
        List<Order> orders=orderService.getOrdersBySalesPersonId(salesId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable long id,@RequestBody String status){
        Order order=orderService.updateOrderStatus(id,status);
        return ResponseEntity.ok(order);
    }
}

