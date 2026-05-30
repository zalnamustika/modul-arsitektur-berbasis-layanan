package com.zalna.auth_service.controller;

import com.zalna.auth_service.dto.AuthResponse;
import com.zalna.auth_service.dto.LoginRequest;
import com.zalna.auth_service.dto.RegisterRequest;
import com.zalna.auth_service.model.User;
import com.zalna.auth_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController 
@RequestMapping("/api/auth") 
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            String token = authService.authenticate(loginRequest); // Menghasilkan token JWT
            User user = authService.getCurrentUser(loginRequest.getUsername()); // Mendapatkan informasi pengguna
            
            AuthResponse response = new AuthResponse(
                token,
                "Bearer",
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                86400000L // 24 jam dalam milliseconds
            );
            
            return ResponseEntity.ok(response); // Mengembalikan token dan informasi pengguna
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Login gagal: " + e.getMessage());
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) { 
        try {
            User user = authService.register(registerRequest); 
            return ResponseEntity.status(HttpStatus.CREATED).body(user);  //
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                Boolean isValid = authService.validateToken(token);
                return ResponseEntity.ok(isValid);
            }
            return ResponseEntity.badRequest().body("Invalid token format"); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token invalid");
        }
    }
}