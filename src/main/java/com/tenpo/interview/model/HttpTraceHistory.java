package com.tenpo.interview.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.security.core.Authentication;

/**
 * This object contains all the info related to the request to save in history.
 *
 * @author Agustin-Varela
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpTraceHistory {

    protected String requestBody;
    protected String responseBody;
    protected HttpTrace httpTrace;
    protected Authentication principal;

}
