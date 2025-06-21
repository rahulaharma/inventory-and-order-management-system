package org.example.Service;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.Customer;
import org.example.model.Order;
import org.example.model.User;
import org.example.repository.CustomerRepo;
import org.example.repository.OrderRepo;
import org.example.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService{
    @Autowired
    private OrderRepo orderRepo;
    private UserRepo userRepo;
    private CustomerRepo customerRepo;
    @Override
    public Order createOrder(Order order) {
        return orderRepo.save(order);
    }
    @Override
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
    @Override
    public Order getOrderById(long id) {

        return orderRepo.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Order not found with id:"+id));
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
