package org.example.repository;

import org.example.model.Customer;
import org.example.model.Order;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    List<Order> findBySalesPerson(User salesperson);
    List<Order> findByCustomer(Customer customer);
}
