package com.cannon.nop.domain.eventjoin.model;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@RedisHash(value = "EventJoinId")
@Getter
@ToString
public class EventJoinId implements Serializable {
    private String primaryId;
    @Indexed
    private String eventUrlUUID;
    @Setter
    private Long joinId;
    public EventJoinId() {}
    @Builder
    public EventJoinId(String eventUrlUUID, String primaryId, Long joinId) {
        this.eventUrlUUID = eventUrlUUID;
        this.primaryId = primaryId;
        this.joinId = joinId;
    }
}
