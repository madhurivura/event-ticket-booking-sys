package com.madhuri.event_ticket_booking_sys.repository;

import com.madhuri.event_ticket_booking_sys.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepo extends JpaRepository<Event,String> {
}
