package com.cannon.nop.application.impl;

import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.domain.event.model.EventResult;
import com.cannon.nop.domain.eventauth.model.EventAuth;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static com.cannon.nop.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventResultServiceImplTest {

    @InjectMocks
    private EventResultServiceImpl eventResultService;
    @Mock
    private EventAuthServiceImpl eventAuthService;
    @Mock
    private EventServiceImpl eventService;
    @Mock
    private EventJoinServiceImpl eventJoinService;


    private Event expectedEvent;
    private EventAuth expectedEventAuth;
    @BeforeEach
    void setUp() {
        expectedEvent = aEvent().build();
        expectedEventAuth = aEventAccessManagement().build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void authenticateAndFetchResults() {
        when(eventAuthService.getEventAccessManagement(expectedEventAuth.getAdminUrlUUID()))
                .thenReturn(expectedEventAuth);


        when(eventService.getEventQuestionForm(expectedEventAuth.getEventUrlUUID())).thenReturn(expectedEvent);
        when(eventJoinService.getJoinEvents(expectedEventAuth.getEventUrlUUID())).thenReturn(Arrays.asList(aEventJoin().build()));

        EventResult actualResult = eventResultService.authenticateAndFetchResults(
                expectedEventAuth.getAdminUrlUUID(),
                expectedEventAuth.getAdminKeyUUID()
        );
        assertThat(actualResult).isNotNull();
    }
}