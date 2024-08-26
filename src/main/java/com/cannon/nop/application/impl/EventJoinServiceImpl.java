package com.cannon.nop.application.impl;

import com.cannon.nop.application.EventJoinService;
import com.cannon.nop.application.EventService;
import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.domain.eventjoin.EventJoinRepository;
import com.cannon.nop.domain.eventjoin.model.EventJoin;
import com.cannon.nop.interfaces.config.exception.ApiException;
import com.cannon.nop.interfaces.config.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class EventJoinServiceImpl implements EventJoinService {
    private final EventJoinRepository eventJoinRepository;
    private final EventService eventService;
    @Override
    public EventJoin joinEvent(EventJoin eventJoin){
        Event event = eventService.getEventQuestionForm(eventJoin.getEventJoinId().getEventUrlUUID());
        if(event.getStartDate().isAfter(eventJoin.getDatetime())) throw new ApiException(ErrorCode.NOT_EVENT_JOIN_TIME);
        return eventJoinRepository.save(eventJoin);
    }
    @Override
    public List<EventJoin> getJoinEvents(String eventUrlUUID) {
        return eventJoinRepository.findAllByEventUrlUUID(eventUrlUUID);
    }
}
