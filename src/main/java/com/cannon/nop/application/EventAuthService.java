package com.cannon.nop.application;

import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.domain.eventauth.model.EventAuth;

public interface EventAuthService {
    EventAuth createEventAccessManagement(Event event);
    EventAuth getEventAccessManagement(String adminUrlUUID);
}
