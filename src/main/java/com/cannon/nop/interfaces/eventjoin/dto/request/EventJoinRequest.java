package com.cannon.nop.interfaces.eventjoin.dto.request;


import com.cannon.nop.interfaces.validator.MaxLength;
import com.cannon.nop.interfaces.validator.NoSpecialCharacters;
import com.cannon.nop.interfaces.validator.NoWhitespace;
import com.cannon.nop.interfaces.validator.NotBlank;
import lombok.Data;

import java.util.List;


@Data
public class EventJoinRequest {
    private String eventUrlUUID;
    @NoSpecialCharacters
    @MaxLength
    @NotBlank
    @NoWhitespace
    private String primaryId;
    private List<EventAnswerFormRequest> formData;

}
