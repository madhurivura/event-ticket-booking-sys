package com.madhuri.event_ticket_booking_sys.dto;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.madhuri.event_ticket_booking_sys.entity.Ticket;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {
    private String name;
    private String email;
    private String role;
}
