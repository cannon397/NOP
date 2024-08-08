package com.cannon.nop.infrastructure.persistence.eventjoin;

import com.cannon.nop.domain.eventjoin.EventJoinRepository;
import com.cannon.nop.domain.eventjoin.model.EventJoin;
import com.cannon.nop.infrastructure.config.redis.Paging;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Repository
public class RedisEventJoinRepositoryImpl implements EventJoinRepository {
    private static final Logger log = LoggerFactory.getLogger(RedisEventJoinRepositoryImpl.class);
    private final RedisTemplate<String, Object> eventRedisTemplate;
    private final Jackson2HashMapper mapper = new Jackson2HashMapper(false);
    private final ObjectMapper objectMapper;

    private final String redisHash = "EventJoin:";
    private final RedisScript<Boolean> script;
    private final StringRedisTemplate stringRedisTemplate;
    @Autowired
    public RedisEventJoinRepositoryImpl(RedisTemplate<String, Object> eventRedisTemplate,
                                        ObjectMapper objectMapper,
                                        RedisScript<Boolean> script,
                                        StringRedisTemplate stringRedisTemplate) {
        this.eventRedisTemplate = eventRedisTemplate;
        this.objectMapper = objectMapper;
        this.script = script;
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @Override
    public boolean setIfAbsent(EventJoin value) {
        String key = redisHash + value.getId();
        Map<String, Object> dataMap = mapper.toHash(value);
        return Boolean.TRUE.equals(eventRedisTemplate.opsForValue().setIfAbsent(key, dataMap));
    }

    @Override
    public EventJoin save(EventJoin value) {
        String eventUrlUUID = value.getEventJoinId().getEventUrlUUID();
        String key = redisHash + eventUrlUUID;
        String primaryId = value.getEventJoinId().getPrimaryId();
        String eventKey = "Event:" + eventUrlUUID;

        try {
            String dataMap = objectMapper.writeValueAsString(value);
            boolean isSaved = executeScript(key, primaryId, dataMap, eventKey);
            if (isSaved)
                return value;
            else
                throw new DuplicateKeyException("이미 존재하는 아이디입니다.");

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing EventJoin object", e);
        }
    }

    private boolean executeScript(String key, String primaryId, String dataMap, String eventKey) {
        Boolean result = eventRedisTemplate.execute(script, Arrays.asList(key, primaryId, dataMap, eventKey));
        return Boolean.TRUE.equals(result);
    }


    @Override
    public List<EventJoin> findAllByEventUrlUUID(String eventUrlUUID) {
        String key = redisHash + eventUrlUUID + ":list";
        List<Object> eventJoinsFromRedis  = eventRedisTemplate.opsForList().range(key,0, -1);
        List<EventJoin> eventJoins = new ArrayList<>();
        for (Object eventJoin : eventJoinsFromRedis) {
            try {
                String jsonStr = objectMapper.writeValueAsString(eventJoin);
                EventJoin eventJoinObj = objectMapper.readValue(jsonStr,EventJoin.class);
                eventJoins.add(eventJoinObj);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return eventJoins;
    }

    @Override
    public boolean delete(String eventUrlUUID) {
        eventRedisTemplate.delete(redisHash + eventUrlUUID + ":INCR");
        eventRedisTemplate.delete(redisHash + eventUrlUUID + ":list");
        return Boolean.TRUE.equals(eventRedisTemplate.delete(redisHash + eventUrlUUID));
    }


}
