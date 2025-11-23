package org.example.controller;

import org.example.Service.ProductService;
import org.example.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")

public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService){
        this.productService=productService;
    }
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product saved=productService.createProduct(product);
        return ResponseEntity.status(201).body(saved);
    }
    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id){
        return productService.getProductById(id)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id,@RequestBody Product product){
        Product updated=productService.updateProduct(id,product);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
