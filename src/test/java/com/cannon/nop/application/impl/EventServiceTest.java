package com.cannon.nop.application.impl;

import com.cannon.nop.application.EventService;
import com.cannon.nop.domain.event.EventRepository;
import com.cannon.nop.domain.event.model.Event;
import lombok.extern.slf4j.Slf4j;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Slf4j
@DisplayName("이벤트개최자서비스 테스트")
@ExtendWith(MockitoExtension.class)
class EventServiceTest {





    @Mock
    private EventRepository eventRepository;
    @InjectMocks
    private EventService eventService;


    private Event expectedEvent;


    @BeforeEach
    void initAll() {
        expectedEvent = aEvent().build();
    }

    @Test
    void createEvent() {
        when(eventRepository.save(expectedEvent)).thenReturn(expectedEvent);
        assertThat(eventService.createEvent(expectedEvent)).isNotNull();
    }

    @Test
    void getEvent(){
        when(eventRepository.findById(expectedEvent.getEventUrlUUID())).thenReturn(Optional.of(expectedEvent));
        assertThat(eventService.getEvent(expectedEvent.getEventUrlUUID())).isNotNull();
    }
}