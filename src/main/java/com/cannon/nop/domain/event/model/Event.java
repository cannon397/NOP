package com.cannon.nop.domain.event.model;

import com.cannon.nop.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@RedisHash(value = "Event")
@ToString
public class Event implements Serializable {
    @Setter
    @Id
    private String eventUrlUUID;
    private String adminKeyUUID;
    private String primaryId;
    private List<EventQuestionForm> eventQuestionForms = new ArrayList<>();
    private Integer joinLimit;
    private LocalDateTime startDate;

    @JsonIgnore
    private LocalDateTime expirationTime = LocalDateTime.now().plus(Duration.ofDays(30));
//    @JsonIgnore
//    private LocalDateTime expirationTime = LocalDateTime.now().plus(Duration.ofSeconds(30));

    @TimeToLive
    public Long getTtl() {
        return Duration.between(startDate, expirationTime).getSeconds();
    }
    Event() {
    }

    @Builder
    public Event(List<EventQuestionForm> eventQuestionForms,
                 String eventUrlUUID,
                 String adminKeyUUID,
                 String primaryId,
                 LocalDateTime startDate,
                 Integer joinLimit) {
        this.eventQuestionForms.addAll(eventQuestionForms);
        this.eventUrlUUID = eventUrlUUID != null ? eventUrlUUID : Util.getUUID();
        this.adminKeyUUID = adminKeyUUID != null ? adminKeyUUID: Util.getUUID();
        this.primaryId = primaryId;
        this.startDate = startDate;
        this.joinLimit = joinLimit;
    }

}
