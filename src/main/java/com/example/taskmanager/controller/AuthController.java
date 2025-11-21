package com.example.taskmanager.controller;

import com.example.taskmanager.dto.ApiResponse;
import com.example.taskmanager.dto.AuthRequest;
import com.example.taskmanager.dto.AuthResponse;
import com.example.taskmanager.model.Role;
import com.example.taskmanager.model.User;
import com.example.taskmanager.service.AuthService;
import com.example.taskmanager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody AuthRequest req) {
        User u = userService.register(req.getUsername(), req.getPassword(), Set.of(Role.ROLE_USER));
        return ResponseEntity.ok(new ApiResponse<>(true, u, "User registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest req) {
        String token = authService.login(req.getUsername(), req.getPassword());
        return ResponseEntity.ok(new ApiResponse<>(true, new AuthResponse(token), "Authenticated"));
    }
}
