package com.tenpo.interview.model.request;

import com.tenpo.interview.validatior.PasswordValueMatch;
import com.tenpo.interview.validatior.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * This object contains info related to create an user.
 *
 * @author Agustin-Varela
 */
@PasswordValueMatch.List({
        @PasswordValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match!"
        )
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistryRequest {

    @NotBlank(message = "User name is missing.")
    @Length(min = 4 , message = "The min size for user name is 4.")
    private String username;

    @NotBlank(message = "Email is missing.")
    @Email(message = "Invalid e-mail.")
    private String email;

    @NotBlank(message = "Password is missing.")
    @ValidPassword
    private String password;

    @NotBlank(message = "Confirm Password is missing.")
    @ValidPassword
    private String confirmPassword;

}
