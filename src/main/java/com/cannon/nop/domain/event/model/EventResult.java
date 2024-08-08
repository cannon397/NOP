package com.cannon.nop.domain.event.model;

import com.cannon.nop.domain.eventjoin.model.EventJoin;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@ToString
public class EventResult {
    private Event event;
    private List<EventJoin> eventJoins = new ArrayList<>();

    @Builder
    public EventResult(Event event, List<EventJoin> eventJoins) {
        this.event = event;
        this.eventJoins.addAll(eventJoins);
    }
}
