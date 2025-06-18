package org.example.repository;

import org.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ProductRepo extends JpaRepository<Product,Long> {
    Optional<Product> findBySku(String sku);
}
