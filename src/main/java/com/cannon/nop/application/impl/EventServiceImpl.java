package com.cannon.nop.application.impl;

import com.cannon.nop.application.EventAuthService;
import com.cannon.nop.application.EventService;
import com.cannon.nop.domain.event.EventRepository;
import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.domain.eventauth.model.EventAuth;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);
    private final EventRepository eventRepository;
    private final EventAuthService eventAuthService;





    @Override
    public EventAuth createEvent(Event event){
        eventRepository.save(event);
        return eventAuthService.createEventAccessManagement(event);
    }

    @Override
    public Event getEventQuestionForm(String eventUrlUUID) {
        return eventRepository.findById(eventUrlUUID).orElseThrow(IllegalArgumentException::new);
    }
}
