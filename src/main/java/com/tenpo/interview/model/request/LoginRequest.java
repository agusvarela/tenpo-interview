package com.tenpo.interview.model.request;

import com.tenpo.interview.validator.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * This object contains info related to user for the login.
 *
 * @author Agustin-Varela
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "User name is missing.")
    @Length(min = 4 , message = "The min size for user name is 4.")
    private String username;

    @NotBlank(message = "Password is missing.")
    @ValidPassword
    private String password;

}
