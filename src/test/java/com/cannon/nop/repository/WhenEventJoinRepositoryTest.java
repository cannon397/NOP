package com.cannon.nop.repository;

import com.cannon.nop.domain.event.EventRepository;
import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.domain.eventjoin.EventJoinRepository;
import com.cannon.nop.domain.eventjoin.model.EventJoin;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static com.cannon.nop.Fixtures.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@DisplayName("이벤트 참가자 CRUD 테스트")
@SpringBootTest
class EventJoinRepositoryTest {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventJoinRepository eventJoinRepository;
    private Event event;
    @BeforeEach
    void initAll(){
        event = eventRepository.save(aEvent().build());
    }
    @AfterEach
    void clearAll(){
        eventRepository.delete(event);
        eventJoinRepository.delete(aEventJoinId().build().getEventUrlUUID());
    }

    @Test
    void 이벤트_참가하기() {
        EventJoin eventJoin = eventJoinRepository.save(aEventJoin().build());
        assertThat(eventJoin).isNotNull();
    }
//    @Disabled
    @Test
    void 이벤트_질문폼_가져오기(){
        Event actualEvent = eventRepository.findById(event.getEventUrlUUID()).orElseThrow(IllegalArgumentException::new);
        assertThat(actualEvent.getPrimaryId()).isEqualTo(event.getPrimaryId());
    }
}
