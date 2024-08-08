package com.cannon.nop.interfaces.validator;


import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotBlank.NoBlankValidator.class)
@Target( {ElementType.TYPE_USE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlank {
    String message() default "필수 입력란은 비어있을 수 없습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    class NoBlankValidator implements ConstraintValidator<MaxLength, String> {

        @Override
        public void initialize(MaxLength constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return !value.isEmpty();
        }
    }
}
