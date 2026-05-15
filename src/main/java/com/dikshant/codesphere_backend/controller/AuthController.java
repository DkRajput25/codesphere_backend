package com.dikshant.codesphere_backend.controller;

import com.dikshant.codesphere_backend.model.User;
import com.dikshant.codesphere_backend.security.JwtService;
import com.dikshant.codesphere_backend.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        String email = data.get("email");
        String password = data.get("password");

        if (authService.usernameExists(username)) {
            return ResponseEntity.badRequest().body(Map.of("error", "username_taken"));
        }

        authService.register(username, email, password);
        return ResponseEntity.ok(Map.of("message", "registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        String password = data.get("password");

        System.out.println("🟢 Login request received: " + username);

        boolean valid = authService.validateLogin(username, password);
        if (!valid) {
            System.out.println("❌ Invalid credentials for: " + username);
            return ResponseEntity.badRequest().body(Map.of("error", "invalid_credentials"));
        }

        String token = jwtService.generateToken(username);
        System.out.println("✅ Login success: " + username);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", username
        ));
    }
}
