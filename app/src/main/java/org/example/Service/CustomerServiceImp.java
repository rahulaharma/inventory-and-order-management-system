package org.example.Service;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.Customer;
import org.example.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CustomerServiceImp implements CustomerService{
    @Autowired
    private CustomerRepo customerRepo;
    @Override
    public Customer registerCustomer(Customer customer) {
        return customerRepo.save(customer);
    }
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public Customer updateCustomer(long id, Customer updated) {
        Customer existing = customerRepo.findById(id)
                        .orElseThrow(()->new EntityNotFoundException("Customer not found for id:"+id));
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setAddress(updated.getAddress());
        return customerRepo.save(existing);
    }
    @Override
    public Optional<Customer> getCustomerById(long id) {
        return customerRepo.findById(id);
    }
    @Override
    public void deleteCustomer(long id) {
        customerRepo.deleteById(id);
    }
}
