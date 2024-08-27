package com.cannon.nop.interfaces.event.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record EventResponseDto(
        String primaryId,
        List<EventQuestionFormDto> formData,
        LocalDateTime startDate
        ) {
}
