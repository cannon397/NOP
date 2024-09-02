package com.cannon.nop.interfaces.eventjoin.dto.response;


import java.util.List;

public record EventJoinResponse(
        String primaryId,
        List<EventAnswerFormResponse> formData

) {
}
