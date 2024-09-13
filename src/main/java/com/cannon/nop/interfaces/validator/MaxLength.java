package com.cannon.nop.interfaces.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;


@Constraint(validatedBy = MaxLength.MaxLengthValidator.class)
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxLength {
    String message() default "문자열의 길이는 50자를 초과할 수 없습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class MaxLengthValidator implements ConstraintValidator<MaxLength, String> {
        private static final int MAX_LENGTH = 50;

        @Override
        public void initialize(MaxLength constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) return true;
            return value.length() <= MAX_LENGTH;
        }
    }
}



