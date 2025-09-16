package com.madhuri.event_ticket_booking_sys.service;

import com.madhuri.event_ticket_booking_sys.entity.Event;
import com.madhuri.event_ticket_booking_sys.exception.ResourceNotFoundException;
import com.madhuri.event_ticket_booking_sys.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    EventRepo eventRepo;

    public Event createEvent(Event event) {
        Event event1 = new Event();

        event1.setDate(event.getDate());
        event1.setTitle(event.getTitle());
        event1.setDescription(event.getDescription());
        event1.setLocation(event.getLocation());
        event1.setTotalTickets(event.getTotalTickets());
        event1.setPricePerTicket(event.getPricePerTicket());

        // Set available tickets = total tickets when creating
        event1.setAvailableTickets(event.getTotalTickets());

        return eventRepo.save(event1);
    }


    public List<Event> getAllEvents() {
        List<Event> events = eventRepo.findAll();
        return events;
    }

    public Event getById(String id) {
        return eventRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }

    public Event updateEvent(String id, Event updated) {
        Event e = getById(id);
        e.setTitle(updated.getTitle());
        e.setDescription(updated.getDescription());
        e.setLocation(updated.getLocation());
        e.setDate(updated.getDate());
        e.setTotalTickets(updated.getTotalTickets());
        e.setAvailableTickets(updated.getAvailableTickets());
        e.setPricePerTicket(updated.getPricePerTicket());
        // if totalTickets changed, adjust availableTickets proportionally (simple approach)
        if (updated.getTotalTickets() > 0) {
            int booked = e.getTotalTickets() - e.getAvailableTickets(); // already booked count
            e.setTotalTickets(updated.getTotalTickets());
            e.setAvailableTickets(Math.max(0, updated.getTotalTickets() - booked));
        }
        return eventRepo.save(e);
    }

    public String deleteEvent(String id) {
        Event e = getById(id);
        eventRepo.delete(e);
        return "Event deleted successfully";
    }

}
