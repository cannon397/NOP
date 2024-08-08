package com.cannon.nop.domain.eventjoin.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EventAnswerForm {
    private String answerForm;
    public EventAnswerForm() {

    }
    @Builder
    public EventAnswerForm(String answerForm) {
        this.answerForm = answerForm;
    }
}
