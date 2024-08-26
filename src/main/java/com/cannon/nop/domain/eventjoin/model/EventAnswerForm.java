package com.cannon.nop.domain.eventjoin.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EventAnswerForm {
    private String answer;
    public EventAnswerForm() {

    }
    @Builder
    public EventAnswerForm(String answer) {
        this.answer = answer;
    }
}
