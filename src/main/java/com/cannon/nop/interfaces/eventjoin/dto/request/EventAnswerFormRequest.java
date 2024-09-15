package com.cannon.nop.interfaces.eventjoin.dto.request;

import com.cannon.nop.interfaces.validator.MaxLength;
import com.cannon.nop.interfaces.validator.NotBlank;
import lombok.Data;

@Data
public class EventAnswerFormRequest {
    @NotBlank
    @MaxLength
    private String answer;
}
