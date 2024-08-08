package com.cannon.nop.domain.eventjoin;

import com.cannon.nop.domain.eventjoin.model.EventJoin;

import java.util.List;

public interface EventJoinRepository {
    boolean setIfAbsent(EventJoin value);
    EventJoin save(EventJoin value);
    List<EventJoin> findAllByEventUrlUUID(String eventUrlUUID);
    boolean delete(String eventUrlUUID);
}
