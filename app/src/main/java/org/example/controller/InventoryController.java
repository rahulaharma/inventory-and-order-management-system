package org.example.controller;

import org.example.Service.InventoryService;
import org.example.model.Inventory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;
    public InventoryController(InventoryService inventoryService){
        this.inventoryService=inventoryService;
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable long productId){
        Inventory inventory=inventoryService.getInventoryByProductId(productId);
        return ResponseEntity.ok(inventory);
    }
    @PutMapping("/product/{productId}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable long productId,@RequestBody int quantity){
        Inventory updatedInventory=inventoryService.updateInventory(productId,quantity);
        return ResponseEntity.ok(updatedInventory);
    }

}
