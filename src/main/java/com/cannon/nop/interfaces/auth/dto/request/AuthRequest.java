package com.cannon.nop.interfaces.auth.dto.request;


public record AuthRequest(
        String eventUrlUUID,
        String adminKeyUUID
) {
}
