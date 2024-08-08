package com.cannon.nop.application;

import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.domain.eventauth.model.EventAuth;
import com.cannon.nop.domain.event.model.EventResult;

public interface EventService {
    EventAuth createEvent(Event event);
    Event getEventQuestionForm(String eventUrlUUID);

}
