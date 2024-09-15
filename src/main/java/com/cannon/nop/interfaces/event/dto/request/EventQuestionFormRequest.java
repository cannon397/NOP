package com.cannon.nop.interfaces.event.dto.request;

import com.cannon.nop.interfaces.validator.MaxLength;
import com.cannon.nop.interfaces.validator.NotBlank;
import lombok.Data;

@Data
public class EventQuestionFormRequest {
    @NotBlank
    @MaxLength
    private String question;
}
