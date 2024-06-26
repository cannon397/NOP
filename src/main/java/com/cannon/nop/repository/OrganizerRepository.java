package com.cannon.nop.repository;

import com.cannon.nop.entity.Organizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizerRepository  {
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public OrganizerRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(Organizer organizer) {
        String key = "Organizer:" + organizer.getUuid(); // 고유한 키 생성
        redisTemplate.opsForHash().put(key, organizer.getUuid(), organizer);
    }

    public Organizer findById(String uuid) {
        String key = "Organizer:" + uuid;
        return (Organizer) redisTemplate.opsForHash().get(key, uuid);
    }
}
