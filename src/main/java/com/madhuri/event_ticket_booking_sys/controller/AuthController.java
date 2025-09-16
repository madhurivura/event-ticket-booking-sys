package com.madhuri.event_ticket_booking_sys.controller;

import com.madhuri.event_ticket_booking_sys.dto.LoginRequest;
import com.madhuri.event_ticket_booking_sys.dto.RegisterRequest;
import com.madhuri.event_ticket_booking_sys.entity.User;
import com.madhuri.event_ticket_booking_sys.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Register user
    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    // Login and get JWT
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }
}
