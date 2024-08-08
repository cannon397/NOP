package com.cannon.nop.domain.event.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EventQuestionForm {
    private String questionInput;

    @Builder
    public EventQuestionForm(String questionInput) {
        this.questionInput = questionInput;
    }

    EventQuestionForm() {
    }
}
