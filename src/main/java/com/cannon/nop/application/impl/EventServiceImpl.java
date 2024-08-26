package com.cannon.nop.application.impl;

import com.cannon.nop.application.EventAuthService;
import com.cannon.nop.application.EventService;
import com.cannon.nop.domain.event.EventRepository;
import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.domain.eventauth.model.EventAuth;
import com.cannon.nop.interfaces.config.exception.ApiException;
import com.cannon.nop.interfaces.config.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventAuthService eventAuthService;





    @Override
    public EventAuth createEvent(Event event){
        eventRepository.save(event);
        return eventAuthService.createEventAccessManagement(event);
    }

    @Override
    public Event getEventQuestionForm(String eventUrlUUID) {
        return eventRepository.findById(eventUrlUUID).orElseThrow(()->new ApiException(ErrorCode.NOT_FOUNT_RESOURCE));
    }
}
