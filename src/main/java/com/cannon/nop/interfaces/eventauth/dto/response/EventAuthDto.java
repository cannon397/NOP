package com.cannon.nop.interfaces.eventauth.dto.response;

import lombok.Data;


@Data
public class EventAuthDto {
    private String adminUrlUUID;
    private String eventUrlUUID;
    private String adminKeyUUID;
}
