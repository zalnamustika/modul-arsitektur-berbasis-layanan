package main.java.com.example.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    @LoadBalanced  // ← supaya RestTemplate bisa resolve nama service dari Eureka
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}