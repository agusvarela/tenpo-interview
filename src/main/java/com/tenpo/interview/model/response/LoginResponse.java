package com.tenpo.interview.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This object contains info related to jwtToken generated after user login.
 *
 * @author Agustin-Varela
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String jwtToken;

}
