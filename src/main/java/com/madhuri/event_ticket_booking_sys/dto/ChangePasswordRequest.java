package com.madhuri.event_ticket_booking_sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChangePasswordRequest {
    private String email;      // user enters email
    private String oldPassword;
    private String newPassword;
}