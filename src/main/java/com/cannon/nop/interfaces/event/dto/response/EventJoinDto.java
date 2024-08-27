package com.cannon.nop.interfaces.event.dto.response;


import java.util.List;

public record EventJoinDto(
        String primaryId,
        List<EventAnswerFormDto> formData

) {
}
