package com.cannon.nop.interfaces.eventjoin.dto.request;


import com.cannon.nop.interfaces.validator.MaxLength;
import com.cannon.nop.interfaces.validator.NoSpecialCharacters;
import com.cannon.nop.interfaces.validator.NotBlank;
import lombok.Data;

import java.util.List;


@Data
public class EventJoinDto {
    private String eventUrlUUID;
    @NoSpecialCharacters
    @MaxLength
    @NotBlank
    private String primaryId;
    private List<@NotBlank @NoSpecialCharacters @MaxLength EventAnswerFormDto> formData;

}
