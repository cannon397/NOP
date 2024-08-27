package com.cannon.nop.application.impl;

import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.domain.eventjoin.EventJoinRepository;
import com.cannon.nop.domain.eventjoin.model.EventJoin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;

import static com.cannon.nop.Fixtures.aEvent;
import static com.cannon.nop.Fixtures.aEventJoin;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayName("유저이벤트서비스 테스트")
@ExtendWith(MockitoExtension.class)
class EventJoinServiceImplTest {


    private static final Logger log = LoggerFactory.getLogger(EventJoinServiceImplTest.class);
    @InjectMocks
    EventJoinServiceImpl eventJoinService;
    @Mock
    EventJoinRepository eventJoinRepository;
    @Mock
    EventServiceImpl eventService;

    private EventJoin eventJoin;
    private Event expectedEvent;
    @BeforeEach
    void initAll(){
        eventJoin = aEventJoin().build();
        expectedEvent = aEvent().build();
    }

    @Test
    public void joinEvent(){
        when(eventService.getEventQuestionForm(eventJoin.getEventJoinId().getEventUrlUUID())).thenReturn(expectedEvent);
        EventJoin isSaved = eventJoinService.joinEvent(aEventJoin().build());
        log.info("이벤트 참가 성공 여부: {}",isSaved);
    }

    @DisplayName("이벤트 중복 참여 막기")
    @Test
    void preventToDuplicateJoinEvent() {
        when(eventService.getEventQuestionForm(expectedEvent.getEventUrlUUID())).thenReturn(expectedEvent);
        when(eventJoinRepository.save(eventJoin)).thenReturn(eventJoin);

        assertThat(eventJoinService.joinEvent(eventJoin).getId()).isEqualTo("test_eventUrlUUID|cannon397|1");
        when(eventJoinRepository.save(eventJoin)).thenThrow(new DuplicateKeyException("이미 존재하는 아이디입니다."));

        // RuntimeException 예외 발생 확인
        Exception exception = assertThrows(RuntimeException.class, () -> {
            eventJoinService.joinEvent(eventJoin);
        });
        assertThat(exception.getMessage()).isEqualTo("이미 존재하는 아이디입니다.");
    }
}
