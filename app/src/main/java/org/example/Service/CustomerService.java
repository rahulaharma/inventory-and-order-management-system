package org.example.Service;

import org.example.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer registerCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer updateCustomer(long id, Customer updated);
    Optional<Customer> getCustomerById(long id);
    void deleteCustomer(long id);
}
