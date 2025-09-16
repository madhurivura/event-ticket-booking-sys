package com.madhuri.event_ticket_booking_sys.controller;


import com.madhuri.event_ticket_booking_sys.dto.TicketRequest;
import com.madhuri.event_ticket_booking_sys.entity.Ticket;
import com.madhuri.event_ticket_booking_sys.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @PreAuthorize("hasAnyRole('ADMIN','USER)")
    @PostMapping("/book")
    public Ticket bookTicket(@RequestBody TicketRequest ticketRequest) {
        return ticketService.bookTicket(ticketRequest);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER)")
    @GetMapping("/user/{userId}")
    public List<Ticket> getTicketsByUser(@PathVariable String userId) {
        return ticketService.getTicketsByUser(userId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER)")
    @PutMapping("/cancel/{ticketId}")
    public Ticket cancelTicket(@PathVariable String ticketId) {
        return ticketService.cancelTicket(ticketId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER)")
    @GetMapping("/user/{userId}/upcoming")
    public List<Ticket> getUpcomingTickets(@PathVariable String userId) {
        return ticketService.getUpcomingTickets(userId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER)")
    @GetMapping("/user/{userId}/attended")
    public List<Ticket> getAttendedTickets(@PathVariable String userId) {
        return ticketService.getAttendedTickets(userId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER)")
    @GetMapping("/user/{userId}/cancelled")
    public List<Ticket> getCancelledTickets(@PathVariable String userId) {
        return ticketService.getCancelledTickets(userId);
    }
}
