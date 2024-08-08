package com.cannon.nop.interfaces.event.dto;

import lombok.Data;

@Data
public class EventResultDTO {
    private String id;
    private String[] question;
    private String[] answer;
}
