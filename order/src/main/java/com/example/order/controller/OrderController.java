package com.example.order.controller;

import com.example.order.model.OrderRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        String json = restTemplate.getForObject(
            "http://product-service/products", String.class
        );
        try {
            List<Map<String, Object>> products = objectMapper.readValue(
                json,
                new TypeReference<List<Map<String, Object>>>() {}
            );
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        String json = restTemplate.getForObject(
            "http://product-service/products/" + id, String.class
        );
        try {
            Map<String, Object> product = objectMapper.readValue(
                json,
                new TypeReference<Map<String, Object>>() {}
            );
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        String json = restTemplate.getForObject(
            "http://product-service/products/" + request.getProductId(), String.class
        );
        try {
            Map<String, Object> product = objectMapper.readValue(
                json,
                new TypeReference<Map<String, Object>>() {}
            );

            if (product == null) {
                return ResponseEntity.badRequest().body("Produk tidak ditemukan");
            }

            BigDecimal price = new BigDecimal(product.get("price").toString());
            BigDecimal quantity = new BigDecimal(request.getQuantity());
            BigDecimal total = price.multiply(quantity);

            Map<String, Object> order = new HashMap<>();
            order.put("orderId", System.currentTimeMillis());
            order.put("product", product);
            order.put("quantity", request.getQuantity());
            order.put("totalPrice", total);
            order.put("status", "CREATED");

            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
