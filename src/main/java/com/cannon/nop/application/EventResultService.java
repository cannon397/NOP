package com.cannon.nop.application;


import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.domain.event.model.EventResult;
import com.cannon.nop.domain.eventjoin.model.EventJoin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EventResultService{

    private final EventService eventService;
    private final EventJoinService eventJoinService;

    public EventResult getEventResult(String eventUrlUUID){
//        EventAdmin eventAdmin = eventAdminService.getEventAccessManagement(adminUrlUUID);
        Event event = eventService.getEvent(eventUrlUUID);
        List<EventJoin> eventJoins = eventJoinService.getJoinEvents(eventUrlUUID);

        EventResult eventResult = EventResult.builder()
                .event(event)
                .eventJoins(eventJoins)
                .build();

        return eventResult;
    }
}
