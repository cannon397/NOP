package com.cannon.nop.interfaces.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Constraint(validatedBy = NoSpecialCharacters.SpecialCharacterValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface NoSpecialCharacters {
    String message() default "특수문자가 포함되어 있습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class SpecialCharacterValidator implements ConstraintValidator<NoSpecialCharacters, String> {
        // 영문자와 숫자를 제외한 특수문자를 찾는 패턴
        private static final String SPECIAL_CHARACTERS = "[^a-zA-Z0-9]";

        @Override
        public void initialize(NoSpecialCharacters constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) return true; // null 값은 유효성 검사의 대상이 아님
            Pattern pattern = Pattern.compile(SPECIAL_CHARACTERS);
            Matcher matcher = pattern.matcher(value);
            return !matcher.find(); // 특수문자가 포함되지 않았을 경우 true 반환
        }
    }
}


