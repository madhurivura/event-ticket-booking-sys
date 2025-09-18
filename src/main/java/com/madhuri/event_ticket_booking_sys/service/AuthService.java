package com.madhuri.event_ticket_booking_sys.service;

import com.madhuri.event_ticket_booking_sys.dto.RegisterRequest;
import com.madhuri.event_ticket_booking_sys.entity.User;
import com.madhuri.event_ticket_booking_sys.repository.UserRepo;
import com.madhuri.event_ticket_booking_sys.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User register(RegisterRequest registerRequest) {
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // hashed
        user.setRole(registerRequest.getRole());
        return userRepo.save(user);
    }

    public String login(String email, String rawPassword) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return "token: "+ jwtUtil.generateToken(user.getEmail());
    }
}
