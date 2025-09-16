package com.madhuri.event_ticket_booking_sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterRequest {
    String name;
    String email;
    String password;
    String role;
}
