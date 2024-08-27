package com.cannon.nop.domain.eventjoin.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RedisHash( value = "EventJoin")
@Getter
@ToString
public class EventJoin implements Serializable {

    private EventJoinId eventJoinId;
    private List<EventAnswerForm> formData = new ArrayList<>();
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime datetime;


    @Id
    private String id;
    @AccessType(AccessType.Type.PROPERTY)
    public String getId() {
        return String.format("%s|%s|%s", eventJoinId.getEventUrlUUID(), eventJoinId.getPrimaryId(), eventJoinId.getJoinId());
    }

    public EventJoin(){

    }
    @Builder
    public EventJoin(EventJoinId eventJoinId,
                     List<EventAnswerForm> formData,LocalDateTime datetime) {
        this.eventJoinId = eventJoinId;
        this.formData.addAll(formData);
        this.datetime = datetime != null ? datetime : LocalDateTime.now();
    }
}
