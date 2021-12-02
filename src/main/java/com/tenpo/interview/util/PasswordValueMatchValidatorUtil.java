package com.tenpo.interview.util;

import com.tenpo.interview.validator.PasswordValueMatch;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * This validator is used as constraint for passwords.
 *
 * @author Agustin-Varela
 */
public class PasswordValueMatchValidatorUtil implements ConstraintValidator<PasswordValueMatch, Object> {

    private String field;
    private String fieldMatch;
    private String message;

    public void initialize(final PasswordValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
        this.message = constraintAnnotation.message();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);

        if (Objects.nonNull(fieldValue)) {
            return fieldValue.equals(fieldMatchValue);
        } else {
            return Objects.isNull(fieldMatchValue);
        }
    }
}