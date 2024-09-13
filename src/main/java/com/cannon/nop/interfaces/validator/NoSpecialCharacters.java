package com.cannon.nop.interfaces.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

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
        private static final String SPECIAL_CHARACTERS = "[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]+";

        @Override
        public void initialize(NoSpecialCharacters constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) return true; // null check is not the responsibility here
            return !value.matches(SPECIAL_CHARACTERS);
        }
    }
}


