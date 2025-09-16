package com.madhuri.event_ticket_booking_sys.repository;

import com.madhuri.event_ticket_booking_sys.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, String> {
    List<Ticket> findByUserId(String userId);
    List<Ticket> findByUserIdAndStatus(String userId, String status);
}
