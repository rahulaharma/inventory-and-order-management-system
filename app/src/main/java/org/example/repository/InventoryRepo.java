package org.example.repository;

import org.example.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface InventoryRepo extends JpaRepository<Inventory,Long> {
    Optional<Inventory> findByProductId(long productId);
}
