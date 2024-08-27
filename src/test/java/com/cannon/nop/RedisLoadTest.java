package com.cannon.nop;

import com.cannon.nop.domain.event.EventRepository;
import com.cannon.nop.domain.event.model.Event;
import com.cannon.nop.domain.eventjoin.EventJoinRepository;
import com.cannon.nop.domain.eventjoin.model.EventJoin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.cannon.nop.Fixtures.*;

@SpringBootTest
public class RedisLoadTest {

    private static final Logger log = LoggerFactory.getLogger(RedisLoadTest.class);
    @Autowired
    EventJoinRepository eventJoinRepository;
    @Autowired
    EventRepository eventRepository;

    private Event event;

    @BeforeEach
    void setUp() {
        event = eventRepository.save(aEvent().build());
    }
//    @AfterEach
//    void tearDown() {
//        eventRepository.delete(event);
//        eventJoinRepository.delete(event.getEventUrlUUID());
//    }

    @Test
    @DisplayName("레디스 성능테스트")
//    @Disabled
    void test(){

        ExecutorService executor = Executors.newFixedThreadPool(200);
        for (int i = 0; i < 100000; i++) {
            final int index = i;
            executor.submit(() -> {
                eventJoinRepository.save(aEventJoin()
                        .eventJoinId(aEventJoinId()
                                .primaryId("test" + index)
                                .build())
                        .formData(Arrays.asList(aEventAnswerForm()
                                .answer("테스트")
                                .build()))
                        .build());
            });
        }
        executor.shutdown();
        try {
            // 모든 작업이 완료되거나 타임아웃이 발생할 때까지 대기합니다.
            if (!executor.awaitTermination(60, TimeUnit.MINUTES)) {
                executor.shutdownNow(); // 타임아웃이 발생한 경우 강제 종료합니다.
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt(); // 현재 스레드를 다시 중단 상태로 설정합니다.
        }


//        List<EventJoin> list = eventJoinRepository.findAllByEventUrlUUID(aEvent().build().getEventUrlUUID());
//        log.info("리스트 정보{}",list.toString());
//        for (EventJoin join : list) {
//            log.info("join Id: {}",join.getEventJoinId().getJoinId());
//        }

        List<EventJoin> pagingResult = eventJoinRepository.findAllByEventUrlUUID(event.getEventUrlUUID());
        for (EventJoin eventJoin : pagingResult) {
            log.info(eventJoin.toString());
        }
    }
}
