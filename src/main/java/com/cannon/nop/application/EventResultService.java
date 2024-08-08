package com.cannon.nop.application;

import com.cannon.nop.domain.event.model.EventResult;

public interface EventResultService {
    EventResult authenticateAndFetchResults(String adminUrlUUID, String adminKeyUUID);
}
