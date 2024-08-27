package com.cannon.nop.application.impl;

import com.cannon.nop.application.EventAuthService;
import com.cannon.nop.domain.event.EventRepository;
import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.domain.eventauth.model.EventAuth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.cannon.nop.Fixtures.aEvent;
import static com.cannon.nop.Fixtures.aEventAccessManagement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("이벤트개최자서비스 테스트")
@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    private static final Logger log = LoggerFactory.getLogger(EventServiceImplTest.class);


    @Mock
    private EventAuthService eventAuthService;
    @Mock
    private EventRepository eventRepository;
    @InjectMocks
    private EventServiceImpl eventService;


    private Event expectedEvent;
    private EventAuth expectedEventAuth;

    @BeforeEach
    void initAll() {
        expectedEvent = aEvent().build();
        expectedEventAuth = aEventAccessManagement().build();
    }

    @Test
    void createEvent() {
        when(eventAuthService.createEventAccessManagement(expectedEvent)).thenReturn(expectedEventAuth);
        when(eventRepository.save(expectedEvent)).thenReturn(expectedEvent);
        assertThat(eventService.createEvent(expectedEvent)).isNotNull();
    }

    @Test
    void getEventQuestionForm(){
        when(eventRepository.findById(expectedEvent.getEventUrlUUID())).thenReturn(Optional.of(expectedEvent));
        assertThat(eventService.getEventQuestionForm(expectedEventAuth.getEventUrlUUID())).isNotNull();
    }
}