package org.example.Service;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.Inventory;
import org.example.repository.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImp implements InventoryService{
    @Autowired
    private InventoryRepo inventoryRepo;
    @Override
    public Inventory updateInventory(long productId, int newQuantity) {
        Inventory inventory=inventoryRepo.findByProductId(productId).
                orElseThrow(()->new EntityNotFoundException("Inventory not found for product ID"+productId));
        inventory.setQuantityAvailable(newQuantity);
        return inventoryRepo.save(inventory);
    }
    @Override
    public Inventory getInventoryByProductId(long productId) {
        return inventoryRepo.findByProductId(productId)
                .orElseThrow(()->new EntityNotFoundException("Inventory not found for product ID: " + productId));
    }

}
