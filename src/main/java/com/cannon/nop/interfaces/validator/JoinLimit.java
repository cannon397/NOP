package com.cannon.nop.interfaces.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Constraint(validatedBy = JoinLimit.JoinLimitValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface JoinLimit {
    String message() default "참가인원은 최소 1이상 50이하 까지 가능합니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class JoinLimitValidator implements ConstraintValidator<JoinLimit, Integer> {
        @Override
        public void initialize(JoinLimit constraintAnnotation) {
        }

        @Override
        public boolean isValid(Integer joinLimit, ConstraintValidatorContext context) {

            return joinLimit > 0 && joinLimit <= 50;
        }
    }
}
