package com.cannon.nop.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static String getUUID(){

        return UUID.randomUUID().toString();
    }
    // 특수문자 포함 여부를 확인하고 예외를 발생시키는 함수
    public static void checkForSpecialCharacters(String input) throws IllegalArgumentException {
        // 특수문자 패턴 정의 (필요에 따라 수정 가능)
        String specialCharacters = "[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]+";
        Pattern pattern = Pattern.compile(specialCharacters);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            throw new IllegalArgumentException("특수문자가 포함되어 있습니다.");
        }
    }
    public static void checkForDefaultCharactersLength(String input) throws IllegalArgumentException {
        if(input.length() > 50) throw new IllegalArgumentException("문자열의 길이는 50자를 초과할 수 없습니다.");
    }
    public static void checkForIsEmptyRequiredCharacters(String input) throws IllegalArgumentException {
        if(input == null) throw new NullPointerException("필수 입력란이 비어있습니다.");
    }
}
