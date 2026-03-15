package com.example.order.controller;

import com.example.order.model.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    // Lihat semua produk via Eureka
    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        Object products = restTemplate.getForObject(
            "http://product-service/products", Object.class
        );
        return ResponseEntity.ok(products);
    }

    // Lihat produk by ID via Eureka
    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Object product = restTemplate.getForObject(
            "http://product-service/products/" + id, Object.class
        );
        return ResponseEntity.ok(product);
    }

    // Buat order baru
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        // Ambil data produk dari product-service via Eureka
        Map<?, ?> product = restTemplate.getForObject(
            "http://product-service/products/" + request.getProductId(), Map.class
        );

        if (product == null) {
            return ResponseEntity.badRequest().body("Produk tidak ditemukan");
        }

        double price = ((Number) product.get("price")).doubleValue();
        double total = price * request.getQuantity();

        Map<String, Object> order = new HashMap<>();
        order.put("orderId", System.currentTimeMillis());
        order.put("product", product);
        order.put("quantity", request.getQuantity());
        order.put("totalPrice", total);
        order.put("status", "CREATED");

        return ResponseEntity.ok(order);
    }
}
