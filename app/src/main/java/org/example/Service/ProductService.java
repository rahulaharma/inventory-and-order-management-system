package org.example.Service;

import org.example.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Optional<Product> getProductById(long id);
    Optional<Product> getProductBySku(String sku);
    Product updateProduct(Long id, Product updated);
    void deleteProduct(long id);


}
