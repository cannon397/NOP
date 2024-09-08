package com.cannon.nop.application;


import com.cannon.nop.domain.event.EventRepository;
import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.interfaces.config.exception.ApiException;
import com.cannon.nop.interfaces.config.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventService {
    private final EventRepository eventRepository;


    public Event createEvent(Event event){
        return eventRepository.save(event);
    }

    public Event getEvent(String eventUrlUUID) {
        return eventRepository.findById(eventUrlUUID).orElseThrow(()->new ApiException(ErrorCode.NOT_FOUND_RESOURCE));
    }
}
