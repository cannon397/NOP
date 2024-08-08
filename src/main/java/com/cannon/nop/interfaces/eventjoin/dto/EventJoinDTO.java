package com.cannon.nop.interfaces.eventjoin.dto;


import com.cannon.nop.interfaces.validator.MaxLength;
import com.cannon.nop.interfaces.validator.NoSpecialCharacters;
import com.cannon.nop.interfaces.validator.NotBlank;
import com.cannon.nop.util.Util;

import lombok.*;

import java.util.List;


@Data
public class EventJoinDTO {
    private String eventUrlUUID;
    @NoSpecialCharacters
    @MaxLength
    @NotBlank
    private String primaryId;
    private List<@NotBlank @NoSpecialCharacters @MaxLength String> formData;

}
