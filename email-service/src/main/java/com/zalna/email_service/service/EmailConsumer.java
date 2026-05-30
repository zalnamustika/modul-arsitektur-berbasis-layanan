
package com.zalna.email_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Map;

@Service

public class EmailConsumer {

    @Autowired

    private EmailSenderService emailSenderService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "orderQueue")

    public void receiveOrder(byte[] body) {

        try {

            String jsonStr = new String(body);
            System.out.println("=== PESAN DITERIMA ===");
            System.out.println(jsonStr);
            System.out.println("=====================");

            Map<String, Object> order = objectMapper.readValue(body, Map.class);
            String email = (String) order.get("email");
            int quantity = (int) order.get("quantity");
            String productName = (String) order.get("productName");
            BigDecimal total = new BigDecimal(order.get("totalPrice").toString());
            System.out.println("productName: " + productName);
            System.out.println("email: " + email);
            emailSenderService.sendEmail(email, productName, quantity, total);
            System.out.println("Email berhasil dikirim ke: " + email);

        } catch (Exception e) {

            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();

        }

    }

}

