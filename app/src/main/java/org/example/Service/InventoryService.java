package org.example.Service;

import org.example.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryService{
    Inventory updateInventory(long productId,int newQuantity);
    List<Inventory> getInventory();
}
