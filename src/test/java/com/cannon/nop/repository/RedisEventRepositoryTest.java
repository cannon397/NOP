package com.cannon.nop.repository;

import com.cannon.nop.TestRedisConfiguration;
import com.cannon.nop.domain.event.EventRepository;
import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.domain.event.model.EventResult;
import com.cannon.nop.domain.eventjoin.EventJoinRepository;
import com.cannon.nop.domain.eventjoin.model.EventJoin;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;


import static com.cannon.nop.Fixtures.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Import(TestRedisConfiguration.class)
@DisplayName("이벤트 주최자 CRUD 테스트")
@SpringBootTest
@ActiveProfiles("test")
class RedisEventRepositoryTest {


    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventJoinRepository eventJoinRepository;


    private Event event;
    private EventJoin eventJoin1, eventJoin2;

    @BeforeEach
    void initAll() {
//      이벤트 생성
        event = eventRepository.save(aEvent().build());
//      참가자 1 이벤트 참여
        eventJoin1 = eventJoinRepository.save(aEventJoin().build());
//      참가자 2  이벤트 참여
        eventJoin2 = eventJoinRepository.save(aEventJoin()
                .eventJoinId(aEventJoinId().primaryId("secondTestId").build())
                .build());
    }

    @AfterEach
    void tearDownAll() {
        eventRepository.delete(event);
        eventJoinRepository.delete(eventJoin1.getEventJoinId().getEventUrlUUID());
        eventJoinRepository.delete(eventJoin2.getEventJoinId().getEventUrlUUID());
    }

    @DisplayName("이벤트 가져오기")
    @Test
    public void getEvent() {
        assertThat(event).isNotNull();
        assertThat(event.getEventUrlUUID()).isEqualTo(aEvent().build().getEventUrlUUID());
    }

    @DisplayName("이벤트 결과 가져오기")
    @Test
    public void getEventResult() {
//      초기 이벤트
        assertThat(event.getPrimaryId()).isEqualTo("질문지_기본키");
        assertThat(eventJoin1).isNotNull();
        assertThat(eventJoin2).isNotNull();

//      이벤트 결과 값 검증
        EventResult eventResult = EventResult.builder()
                .event(eventRepository.findById(event.getEventUrlUUID()).orElseThrow(IllegalArgumentException::new))
                .eventJoins(eventJoinRepository.findAllByEventUrlUUID(event.getEventUrlUUID()))
                .build();
        assertThat(eventResult).isNotNull();

//       이벤트 참여자의 고유식별값 검증
        String actualPrimaryId = eventResult.getEventJoins().get(1).getEventJoinId().getPrimaryId();
        String exceptedPrimaryId = eventJoin2.getEventJoinId().getPrimaryId();
        assertThat(actualPrimaryId)
                .isEqualTo(exceptedPrimaryId);

    }
}

