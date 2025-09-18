package com.madhuri.event_ticket_booking_sys.controller;

import com.madhuri.event_ticket_booking_sys.entity.Event;
import com.madhuri.event_ticket_booking_sys.service.EventService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER)")
    @GetMapping("/all")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER)")
    @GetMapping("/{id}")
    public Event getById(@PathVariable String id) {
        return eventService.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Event update(@PathVariable String id, @RequestBody Event event) {
        return eventService.updateEvent(id, event);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable String id){
        return eventService.deleteEvent(id);
    }
}
