package com.cannon.nop.interfaces.event.dto.request;

import com.cannon.nop.interfaces.validator.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventRequest
{

    @NoSpecialCharacters
    @MaxLength
    @NotBlank
    private String primaryId;
    @Valid
    private List<EventQuestionFormRequest> formData;

    @JoinLimit
    private Integer joinLimit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @FutureOrNow
    private LocalDateTime startDate;

}
