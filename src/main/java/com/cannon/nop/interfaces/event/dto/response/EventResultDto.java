package com.cannon.nop.interfaces.event.dto.response;

import java.util.List;

public record EventResultDto(
        EventResponseDto event,
        List<EventJoinDto> eventJoins
) {

}
