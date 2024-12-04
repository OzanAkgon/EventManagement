package com.example.service;

import com.example.model.Event;
import com.example.model.User;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface EventService {
    public Event createEvent(Event event);
    public Event updateEvent(Long id, Event updatedEvent);
    public void deleteEvent(Long id);
    public List<Event> getAllEvents();
    public Event getEventById(Long id);
	public void joinEvent(Long id, String token);
	public int countParticipantsForEvent(Long eventId);
}
