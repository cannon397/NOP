package com.cannon.nop.domain.event.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EventQuestionForm {
    private String question;

    @Builder
    public EventQuestionForm(String question) {
        this.question = question;
    }

    EventQuestionForm() {
    }
}
