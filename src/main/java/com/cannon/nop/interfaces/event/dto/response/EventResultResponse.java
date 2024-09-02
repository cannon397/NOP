package com.cannon.nop.interfaces.event.dto.response;

import com.cannon.nop.interfaces.config.jsonview.Views;
import com.cannon.nop.interfaces.eventjoin.dto.response.EventJoinResponse;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

public record EventResultResponse(
        @JsonView(Views.WhenGetEventResult.class) EventResponse event,
        List<EventJoinResponse> eventJoins
) {

}
