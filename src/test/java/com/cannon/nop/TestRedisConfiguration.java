package com.cannon.nop;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import redis.embedded.RedisServer;

import java.io.IOException;

@TestConfiguration
@Slf4j

public class TestRedisConfiguration {

    private RedisServer redisServer;

    public TestRedisConfiguration() throws IOException {
        this.redisServer = new RedisServer(6379);
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        log.info("Embedded Redis Server Starting...");
        redisServer.start();
    }
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // 테스트용 임베디드 Redis 서버에 연결하는 Factory 반환
        return new LettuceConnectionFactory("localhost", 6379);
    }
    @PreDestroy
    public void preDestroy() throws IOException {
        redisServer.stop();
    }
}
