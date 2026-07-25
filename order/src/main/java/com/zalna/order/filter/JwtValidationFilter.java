package com.zalna.order.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtValidationFilter extends OncePerRequestFilter {

    private final RestTemplate restTemplate;
    private final String AUTH_SERVICE_URL = "http://AUTH-SERVICE";

    public JwtValidationFilter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.equals("/orders") && request.getMethod().equals("POST")) {

            String authHeader = request.getHeader("Authorization");

            System.out.println("=== JWT FILTER ===");
            System.out.println("Auth Header: " + authHeader);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Token tidak ditemukan. Silakan login terlebih dahulu.\"}");
                return;
            }

            try {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", authHeader);
                HttpEntity<String> entity = new HttpEntity<>(headers);

                System.out.println("Validating token ke: " + AUTH_SERVICE_URL + "/api/auth/validate");

                ResponseEntity<Boolean> validationResponse = restTemplate.exchange(
                    AUTH_SERVICE_URL + "/api/auth/validate",
                    HttpMethod.POST,
                    entity,
                    Boolean.class
                );

                System.out.println("Validation result: " + validationResponse.getBody());

                if (validationResponse.getBody() == null || !validationResponse.getBody()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Token tidak valid atau sudah expired.\"}");
                    return;
                }

            } catch (Exception e) {
                System.err.println("Error validasi token: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Gagal validasi token: " + e.getMessage() + "\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
