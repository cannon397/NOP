package com.cannon.nop.application;

import com.cannon.nop.domain.eventjoin.model.EventJoin;

import java.util.List;


public interface EventJoinService {
    EventJoin joinEvent(EventJoin eventJoin);
    List<EventJoin> getJoinEvents(String eventUrlUUID);
}
