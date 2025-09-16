package org.example.controller;

import org.example.Service.CustomerService;
import org.example.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Controller+response body , only return the data not the view name
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:5173")// ensures that the request from different origions are applicable
public class CustomerController {
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService){
        this.customerService=customerService;
    }
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        Customer saved=customerService.registerCustomer(customer);
        return ResponseEntity.status(201).body(saved);
    }
    @GetMapping
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable long id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable long id, @RequestBody Customer customer) {
        Customer updated = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
