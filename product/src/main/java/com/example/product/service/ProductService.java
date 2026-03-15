package com.example.product.service;

import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // CREATE
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // READ ALL
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // READ BY ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // UPDATE
    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setStock(updatedProduct.getStock());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    // DELETE
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}