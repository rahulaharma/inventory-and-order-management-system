package org.example.Service;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.Inventory;
import org.example.model.OrderItem;
import org.example.model.Product;
import org.example.model.User;
import org.example.repository.InventoryRepo;
import org.example.repository.OrderItemRepo;
import org.example.repository.ProductRepo;
import org.example.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceImp implements ProductService{
    private final ProductRepo productRepo;
    private final OrderItemRepo orderItemRepo;
    private final InventoryRepo inventoryRepo;
    private final ProductNotificationService productNotificationService;

    public ProductServiceImp(ProductRepo productRepo, OrderItemRepo orderItemRepo,InventoryRepo inventoryRepo,ProductNotificationService productNotificationService){
        this.productRepo = productRepo;
        this.orderItemRepo = orderItemRepo;
        this.inventoryRepo=inventoryRepo;
        this.productNotificationService=productNotificationService;
    }
    @Override
    public Product createProduct(Product product) {
        Product savedproduct=productRepo.save(product);
        Inventory inventory=new Inventory();
        inventory.setProduct(savedproduct);
        inventory.setQuantityAvailable(0);
        inventoryRepo.save(inventory);

        productNotificationService.sendNewProductNotification(savedproduct);
        return savedproduct;
    }
    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
    @Override
    public Optional<Product> getProductById(long id) {
        return productRepo.findById(id);
    }

    @Override
    public Optional<Product> getProductBySku(String sku) {
        return productRepo.findBySku(sku);
    }

    @Override
    public Product updateProduct(Long id, Product updated) {
        Product existing = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setSku(updated.getSku());
        return productRepo.save(existing);
    }
    @Override
    public void deleteProduct(long id) {
        productRepo.deleteById(id);
    }
}
