package com.madhuri.event_ticket_booking_sys.service;

import com.madhuri.event_ticket_booking_sys.dto.ChangePasswordRequest;
import com.madhuri.event_ticket_booking_sys.entity.User;
import com.madhuri.event_ticket_booking_sys.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

        @Autowired
        private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

        public List<User> getAllUsers() {
            return userRepo.findAll();
        }

        public User getUserById(String id) {
            return userRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        }


        public User updateUser(String id, User updatedUser) {
            User existingUser = userRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));


            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());


            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            return userRepo.save(existingUser);
        }

        // ✅ Delete user
        public void deleteUser(String id) {
            if (!userRepo.existsById(id)) {
                throw new RuntimeException("User not found with id: " + id);
            }
            userRepo.deleteById(id);
        }

    public String changePassword(ChangePasswordRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepo.save(user);

        return "Password updated successfully";
    }
}
