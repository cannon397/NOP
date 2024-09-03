package com.cannon.nop.interfaces.event.dto.response;

import com.cannon.nop.interfaces.config.jsonview.Views;
import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDateTime;
import java.util.List;

public record EventResponse(
        @JsonView(Views.WhenGetEventResult.class) String eventUrlUUID,
        @JsonView(Views.WhenEventJoin.class) String primaryId,
        @JsonView(Views.WhenEventJoin.class) List<EventQuestionFormResponse> formData,
        @JsonView(Views.WhenEventJoin.class) LocalDateTime startDate,
        @JsonView(Views.WhenGetEventResult.class) Integer joinLimit,
        @JsonView(Views.WhenGetEventResult.class) Integer joinLeft
) {
}
