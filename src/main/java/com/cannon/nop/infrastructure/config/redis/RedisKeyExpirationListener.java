package com.cannon.nop.infrastructure.config.redis;

import com.cannon.nop.domain.eventjoin.EventJoinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyExpirationListener implements MessageListener {
    private static final Logger log = LoggerFactory.getLogger(RedisKeyExpirationListener.class);
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private EventJoinRepository eventJoinRepository;
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = new String(message.getBody());
        String[] subKeys = expiredKey.split(":");
        log.info("만료된 key: {}",subKeys[1]);
        redisTemplate.opsForSet().remove("Event", subKeys[1]); //       CrudRepository를 확장한 엔티티의 경우 ttl 설정을 하더라도 @Id 등의 어노테이션 설정에 의한 더미값이 남습니다.
        eventJoinRepository.delete(subKeys[1]);
    }
}