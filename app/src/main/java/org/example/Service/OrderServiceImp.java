package org.example.Service;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.Customer;
import org.example.model.Order;
import org.example.model.OrderItem;
import org.example.model.User;
import org.example.repository.CustomerRepo;
import org.example.repository.OrderRepo;
import org.example.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService{
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final CustomerRepo customerRepo;
    public OrderServiceImp(OrderRepo orderRepo, UserRepo userRepo, CustomerRepo customerRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.customerRepo = customerRepo;
    }
    @Override
    public Order createOrder(Order order) {
        try {
            order.setCreatedAt(LocalDateTime.now());
            for (OrderItem item : order.getOrderItems()) {
                item.setOrder(order);
            }
            return orderRepo.save(order);
        }
        catch(Exception e){
            e.printStackTrace(); // or use a logger
            throw e;
        }
    }
    @Override
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
    @Override
    public Optional<Order> getOrderById(long id) {
        return orderRepo.findById(id);
    }

    @Override
    public List<Order> getOrdersBySalesPersonId(Long salesPersonId) {
        User salesPerson=userRepo.findById(salesPersonId)
                .orElseThrow(()->new EntityNotFoundException("Sales person not found with id:"+salesPersonId));
        return orderRepo.findBySalesPerson(salesPerson);
    }
    @Override
    public List<Order> getOrdersByCustomerId(Long customerId) {
        Customer customer=customerRepo.findById(customerId)
                .orElseThrow(()->new EntityNotFoundException("Customer not found with id:"+customerId));
        return orderRepo.findByCustomer(customer);
    }
    @Override
    public Order updateOrderStatus(Long orderId, String status) {
        Order order=orderRepo.findById(orderId)
                .orElseThrow(()->new EntityNotFoundException("Order not found for id:"+orderId));
        order.setStatus(status);
        return orderRepo.save(order);
    }
    @Override
    public void deleteOrder(long id) {
        orderRepo.deleteById(id);
    }
}
