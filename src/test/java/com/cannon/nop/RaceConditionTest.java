package com.cannon.nop;

import com.cannon.nop.application.EventJoinService;
import com.cannon.nop.domain.event.EventRepository;
import com.cannon.nop.domain.eventjoin.EventJoinRepository;
import com.cannon.nop.domain.eventjoin.model.EventJoin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.cannon.nop.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestRedisConfiguration.class)
public class RaceConditionTest {

    private static final Logger log = LoggerFactory.getLogger(RaceConditionTest.class);
    @Autowired
    private EventJoinService eventJoinService;

    @Autowired
    private EventJoinRepository eventJoinRepository;

    @Autowired
    private EventRepository eventRepository;

    @AfterEach
    void tearDown() {
        eventJoinRepository.delete(aEvent().build().getEventUrlUUID());
        eventRepository.delete(aEvent().build());
    }
    @BeforeEach
    void setUp() {
        eventRepository.save(aEvent().build());
    }

    @DisplayName("동일한 아이디 이벤트 참가 동시성 검증")
    @Test
//    @Disabled
    public void test() throws InterruptedException, ExecutionException {
        int numberOfThreads = 10; // 동시에 실행할 스레드 수
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<EventJoin>> futures = new ArrayList<>();
        AtomicInteger exceptionCount = new AtomicInteger(0); // 예외 발생 횟수 세기
        for (int i = 0; i < numberOfThreads; i++) {
            int finalI = i;
            Callable<EventJoin> task = () -> {
                    return eventJoinService.joinEvent(aEventJoin()
                        .eventJoinId(aEventJoinId().build())
                        .datetime(LocalDateTime.now().plusMinutes(10))
                        .formData(Arrays.asList(aEventAnswerForm()
                                .answer("랜덤답변" + finalI)
                                .build())).build());
            };
            futures.add(service.submit(task));
        }

        service.shutdown();
        if (!service.awaitTermination(1, TimeUnit.MINUTES)) {
            service.shutdownNow(); // 시간 초과시 강제 종료
        }


        for (Future<EventJoin> future : futures) {
            try {
                future.get();
            } catch (ExecutionException e) {
                log.info(e.getMessage());
                exceptionCount.incrementAndGet();
            }
        }
        //        예외 발생 횟수가 9번인지 확인
        assertThat(exceptionCount.get()).isEqualTo(9);
    }
}
