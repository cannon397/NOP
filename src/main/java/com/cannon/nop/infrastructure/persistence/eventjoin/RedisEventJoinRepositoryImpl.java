package com.cannon.nop.infrastructure.persistence.eventjoin;

import com.cannon.nop.domain.eventjoin.EventJoinRepository;
import com.cannon.nop.domain.eventjoin.model.EventJoin;
import com.cannon.nop.interfaces.config.exception.ConflictException;
import com.cannon.nop.interfaces.config.exception.InternalServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Repository
public class RedisEventJoinRepositoryImpl implements EventJoinRepository {
    private final RedisTemplate<String, Object> eventRedisTemplate;
    private final Jackson2HashMapper mapper = new Jackson2HashMapper(false);
    private final ObjectMapper objectMapper;

    private final String redisHash = "EventJoin:";
    private final RedisScript<Object> script;

    @Autowired
    public RedisEventJoinRepositoryImpl(RedisTemplate<String, Object> eventRedisTemplate,
                                        ObjectMapper objectMapper,
                                        RedisScript<Object> script) {
        this.eventRedisTemplate = eventRedisTemplate;
        this.objectMapper = objectMapper;
        this.script = script;
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
            executeScript(key, primaryId, dataMap, eventKey);
            return value;

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing EventJoin object", e);
        }
    }
    @SuppressWarnings("unchecked")
    private void executeScript(String key, String primaryId, String dataMap, String eventKey) {
        Object result = eventRedisTemplate.execute(script, Arrays.asList(key, primaryId, dataMap, eventKey));
        if (result instanceof Map) {
            Map<String, Object> resultMap = (Map<String, Object>) result;
            Boolean isSuccess = (Boolean) resultMap.get("success");
            String message = (String) resultMap.get("message");
            if (!Boolean.TRUE.equals(isSuccess)) {
                throw new ConflictException(message);
            }
        } else {
            throw new InternalServerException("Unexpected result type from Lua script.");
        }
    }

    @Override
    public List<EventJoin> findAllByEventUrlUUID(String eventUrlUUID) {
        String key = redisHash + eventUrlUUID + ":list";
        List<Object> eventJoinsFromRedis = eventRedisTemplate.opsForList().range(key, 0, -1);
        List<EventJoin> eventJoins = new ArrayList<>();
        if (eventJoinsFromRedis == null) {
            return new ArrayList<>();
        }
        for (Object eventJoin : eventJoinsFromRedis) {
            try {
                String jsonStr = objectMapper.writeValueAsString(eventJoin);
                EventJoin eventJoinObj = objectMapper.readValue(jsonStr, EventJoin.class);
                eventJoins.add(eventJoinObj);
            } catch (JsonProcessingException e) {
                throw new InternalServerException(e.getMessage());
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
