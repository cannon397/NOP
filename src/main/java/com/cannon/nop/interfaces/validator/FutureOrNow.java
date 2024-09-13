package com.cannon.nop.interfaces.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.time.LocalDateTime;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Constraint(validatedBy = FutureOrNow.FutureOrNowValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface FutureOrNow {
    String message() default "이벤트 생성 시간이 현재보다 과거입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class FutureOrNowValidator implements ConstraintValidator<FutureOrNow, LocalDateTime> {
        @Override
        public void initialize(FutureOrNow constraintAnnotation) {
        }

        @Override
        public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext context) {
            if (localDateTime == null) {
                return true;
            }
            return !localDateTime.isBefore(LocalDateTime.now());
        }
    }
}

