package com.cannon.nop.interfaces.config.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    INVALID_INPUT("4001", "잘못된 입력값입니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("4002", "유저를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_EVENT_JOIN_TIME("4003","이벤트 참여시간이 아닙니다.",HttpStatus.BAD_REQUEST),

    UNAUTHORIZED_ERROR("4011", "관리자 KEY가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),

    NOT_FOUNT_RESOURCE("4041","해당 자원을 찾지 못했습니다.",HttpStatus.NOT_FOUND),

    ALREADY_EXIST_ID("4092", "이미 존재하는 아이디입니다.", HttpStatus.CONFLICT),
    NO_PARTICIPANTS_AVAILABLE("4093", "참여 가능 인원이 없습니다.", HttpStatus.CONFLICT),

    INTERNAL_ERROR("5000", "서버 에러", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String userMessage;
    private final HttpStatus httpStatus;

    // 상태 코드로 ErrorCode를 찾는 정적 메서드 추가
    public static ErrorCode fromCode(String code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }
        throw new IllegalArgumentException("Invalid ErrorCode: " + code);
    }
}
