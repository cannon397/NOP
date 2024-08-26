package com.cannon.nop.application.impl;

import com.cannon.nop.application.EventAuthService;
import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.domain.eventauth.EventAuthRepository;
import com.cannon.nop.domain.eventauth.model.EventAuth;
import com.cannon.nop.interfaces.config.exception.ApiException;
import com.cannon.nop.interfaces.config.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventAuthServiceImpl implements EventAuthService {

    private final EventAuthRepository eventAuthRepository;

    @Override
    public EventAuth createEventAccessManagement(Event event) {
        EventAuth eventAuth = EventAuth.builder()
                .eventUrlUUID(event.getEventUrlUUID())
                .build();
        eventAuth.createAdmin();

        return eventAuthRepository.save(eventAuth);
    }

    public EventAuth getEventAccessManagement(String adminUrlUUID) {
        return eventAuthRepository.findById(adminUrlUUID)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    }
}
