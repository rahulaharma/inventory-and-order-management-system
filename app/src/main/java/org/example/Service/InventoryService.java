package org.example.Service;

import org.example.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryService{
    Inventory updateInventory(long productId,int newQuantity);
    Inventory getInventoryByProductId(long productId);
}
