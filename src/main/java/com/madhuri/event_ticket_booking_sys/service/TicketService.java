package com.madhuri.event_ticket_booking_sys.service;

import com.madhuri.event_ticket_booking_sys.dto.TicketRequest;
import com.madhuri.event_ticket_booking_sys.entity.Event;
import com.madhuri.event_ticket_booking_sys.entity.Ticket;
import com.madhuri.event_ticket_booking_sys.entity.User;
import com.madhuri.event_ticket_booking_sys.repository.EventRepo;
import com.madhuri.event_ticket_booking_sys.repository.TicketRepo;
import com.madhuri.event_ticket_booking_sys.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    TicketRepo ticketRepo;

    @Autowired
    EventRepo eventRepo;

    @Autowired
    UserRepo userRepo;

    // ✅ Book a ticket
    public Ticket bookTicket(TicketRequest request) {
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepo.findById(request.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getAvailableTickets() < request.getQuantity()) {
            throw new RuntimeException("Not enough tickets available");
        }

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setEvent(event);
        ticket.setQuantity(request.getQuantity());
        ticket.setStatus("BOOKED");
        ticket.setAttended(false);
        ticket.setTotalPrice(request.getQuantity() * event.getPricePerTicket());

        // update available tickets
        event.setAvailableTickets(event.getAvailableTickets() - request.getQuantity());
        eventRepo.save(event);

        return ticketRepo.save(ticket);
    }

    // ✅ Get all tickets for a user
    public List<Ticket> getTicketsByUser(String userId) {
        return ticketRepo.findByUserId(userId);
    }

    // ✅ Cancel a ticket
    public Ticket cancelTicket(String ticketId) {
        Ticket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (ticket.getStatus() == "CANCELLED") {
            throw new RuntimeException("Ticket already cancelled");
        }

        Event event = ticket.getEvent();
        event.setAvailableTickets(event.getAvailableTickets() + ticket.getQuantity());
        eventRepo.save(event);

        ticket.setStatus("CANCELLED");
        ticket.setAttended(false);

        return ticketRepo.save(ticket);
    }

    // ✅ Upcoming tickets (BOOKED + future events)
    public List<Ticket> getUpcomingTickets(String userId) {
        return ticketRepo.findByUserIdAndStatus(userId, "BOOKED")
                .stream()
                .filter(ticket -> ticket.getEvent().getDate().isAfter(LocalDateTime.now()))
                .toList();
    }

    // ✅ Attended tickets (BOOKED + past events)
    public List<Ticket> getAttendedTickets(String userId) {
        return ticketRepo.findByUserIdAndStatus(userId, "BOOKED")
                .stream()
                .filter(ticket -> ticket.getEvent().getDate().isBefore(LocalDateTime.now()))
                .peek(ticket -> ticket.setAttended(true))
                .toList();
    }

    // ✅ Cancelled tickets
    public List<Ticket> getCancelledTickets(String userId) {
        return ticketRepo.findByUserIdAndStatus(userId,"CANCELLED");
    }
}
