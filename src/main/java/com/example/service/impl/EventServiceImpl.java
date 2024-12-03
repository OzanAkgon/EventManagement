package com.example.service.impl;

import com.example.jwt.JwtService;
import com.example.model.Event;
import com.example.model.RefreshToken;
import com.example.model.User;
import com.example.repository.EventRepository;
import com.example.repository.RefreshTokenRepository;
import com.example.repository.UserRepository;
import com.example.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
    private EventRepository eventRepository;
    
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserRepository userRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long id, Event updatedEvent) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        existingEvent.setTitle(updatedEvent.getTitle());
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setEventDate(updatedEvent.getEventDate());
        existingEvent.setMaxParticipants(updatedEvent.getMaxParticipants());
        return eventRepository.save(existingEvent);
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Override
    public void joinEvent(Long eventId, String token) {
        // Token'den kullanıcı adını al
        String username = jwtService.getUsernameByToken(token);

        // Kullanıcıyı bul
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Etkinliği bul
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Kullanıcı zaten etkinliğe katılmış mı kontrol et
        if (user.getEvents().contains(event)) {
            throw new RuntimeException("User already joined the event");
        }

        // Etkinlik doluysa hata fırlat
       // if (event.getMaxParticipants() > event.getParticipants().size() >= event.getMaxParticipants()) {
          //  throw new RuntimeException("Event is full");
        //}

        // Ara tabloya kaydı eklemek için ilişkiyi güncelle
        user.getEvents().add(event);

        // User kaydet (ara tabloya kaydı tetikler)
        userRepository.save(user);
    }
        
        
    }


