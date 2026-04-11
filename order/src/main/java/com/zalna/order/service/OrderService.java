package com.zalna.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalna.order.model.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void createOrder(Order order) {
        try {
            String json = objectMapper.writeValueAsString(order);
            System.out.println("=== ORDER JSON DIKIRIM KE QUEUE ===");
            System.out.println(json);
            System.out.println("===================================");
        } catch (Exception e) {
            System.err.println("Error serializing order: " + e.getMessage());
        }
        rabbitTemplate.convertAndSend("orderQueue", order);
    }
}
