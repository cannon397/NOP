package com.cannon.nop;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import redis.embedded.RedisServer;

import java.io.IOException;

@Slf4j
@TestConfiguration
public class TestRedisConfiguration {

    private final RedisServer redisServer;

    public TestRedisConfiguration() throws IOException {
        log.info("what is Redis Server Starting...");
        this.redisServer = new RedisServer(6370);
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        log.info("Embedded Redis Server Starting...");
        redisServer.start();
    }
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // 테스트용 임베디드 Redis 서버에 연결하는 Factory 반환
        log.info("Client Connect to Embedded RedisServer...");
        return new LettuceConnectionFactory("localhost", 6370);
    }

    @PreDestroy
    public void preDestroy() throws IOException {
        redisServer.stop();
    }
}
