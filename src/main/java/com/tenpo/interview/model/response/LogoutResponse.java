package com.tenpo.interview.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This object contains the message for logout endpoint.
 *
 * @author Agustin-Varela
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogoutResponse {

    private String message;

}
