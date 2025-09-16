package com.madhuri.event_ticket_booking_sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketRequest {
    String userId;
    String eventId;
    int quantity;
}
