package com.cannon.nop.interfaces.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Constraint(validatedBy = NoWhitespace.NoWhiteSpaceValidator.class)
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoWhitespace {
    String message() default "공백이 포함되어서는 안 됩니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class NoWhiteSpaceValidator implements ConstraintValidator<NoWhitespace, String>  {
        @Override
        public void initialize(NoWhitespace constraintAnnotation) {
        }
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return !value.contains(" ");
        }
    }

}