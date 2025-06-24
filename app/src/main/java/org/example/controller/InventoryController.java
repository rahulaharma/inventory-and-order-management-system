package org.example.controller;

import org.example.Service.InventoryService;
import org.example.model.Inventory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "http://localhost:1234")
public class InventoryController {
    private final InventoryService inventoryService;
    public InventoryController(InventoryService inventoryService){
        this.inventoryService=inventoryService;
    }
    @GetMapping
    public ResponseEntity<List<Inventory>> getInventory(){
        List<Inventory> inventory=inventoryService.getInventory();
        return ResponseEntity.ok(inventory);
    }
    @PutMapping("/product/{productId}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable long productId,@RequestBody int quantity){
        Inventory updatedInventory=inventoryService.updateInventory(productId,quantity);
        return ResponseEntity.ok(updatedInventory);
    }

}
