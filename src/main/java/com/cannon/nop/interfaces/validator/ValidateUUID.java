package com.cannon.nop.interfaces.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidateUUID.UUIDLengthValidator.class)
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface ValidateUUID {
    String message() default "UUID값이 올바르지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class UUIDLengthValidator implements ConstraintValidator<ValidateUUID, String> {

        @Override
        public void initialize(ValidateUUID constraintAnnotation) {
        }

        @Override
        public boolean isValid(String uuid, ConstraintValidatorContext context) {
            return uuid != null && uuid.length() == 36;
        }
    }
}
