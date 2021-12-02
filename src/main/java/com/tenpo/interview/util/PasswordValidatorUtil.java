package com.tenpo.interview.util;

import com.tenpo.interview.validator.ValidPassword;
import org.apache.commons.lang3.StringUtils;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * This validator is used as constraint for passwords.
 *
 * @author Agustin-Varela
 */
public class PasswordValidatorUtil implements ConstraintValidator<ValidPassword, String> {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 30;
    private static final char SEPARATOR = ',';

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (StringUtils.isNotEmpty(password)) {
            PasswordValidator passwordValidator = new PasswordValidator(
                    List.of(new LengthRule(MIN_LENGTH, MAX_LENGTH), new WhitespaceRule()));

            RuleResult result = passwordValidator.validate(new PasswordData(password));

            if (result.isValid()) {
                return true;
            }

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    StringUtils.join(passwordValidator.getMessages(result), SEPARATOR))
                    .addConstraintViolation();
        }
        return false;
    }
}