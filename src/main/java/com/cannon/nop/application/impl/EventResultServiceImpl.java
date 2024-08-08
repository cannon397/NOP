package com.cannon.nop.application.impl;

import com.cannon.nop.application.EventAuthService;
import com.cannon.nop.application.EventJoinService;
import com.cannon.nop.application.EventResultService;
import com.cannon.nop.application.EventService;
import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.domain.event.model.EventResult;
import com.cannon.nop.domain.eventauth.model.EventAuth;
import com.cannon.nop.domain.eventjoin.model.EventJoin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EventResultServiceImpl implements EventResultService {

    private final EventAuthService eventAuthService;
    private final EventService eventService;
    private final EventJoinService eventJoinService;

    @Override
    public EventResult authenticateAndFetchResults(String adminUrlUUID, String adminKeyUUID){
        EventAuth eventAuth = eventAuthService.getEventAccessManagement(adminUrlUUID);
        eventAuth.isAdmin(adminKeyUUID);
        Event event = eventService.getEventQuestionForm(eventAuth.getEventUrlUUID());
//        Event event = eventRepository.findById(eventAuth.getEventUrlUUID()).orElseThrow(IllegalArgumentException::new);
        List<EventJoin> eventJoins = eventJoinService.getJoinEvents(eventAuth.getEventUrlUUID());

        EventResult eventResult = EventResult.builder()
                .event(event)
                .eventJoins(eventJoins)
                .build();

        return eventResult;
    }
}
