package com.tenpo.interview.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * This component is defined to return an message when the request is not authorized by the service.
 *
 * @author Agustin-Varela
 */
@Component
public class JwtAuthentication implements AuthenticationEntryPoint, Serializable {

	private static final String DESCRIPTION_UNAUTHORIZED_REQUEST = "{ \"errorDescription\": \"Unauthorized request\",";
	private static final String MESSAGE_UNAUTHORIZED_REQUEST = "\"errorMessage\": \"Unauthorized resource access\",";
	private static final String TYPE_UNAUTHORIZED_RESOURCE_ACCESS = "\"errorType\": [\"Unauthorized\"]}";

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		response.setContentType(APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getOutputStream().println(DESCRIPTION_UNAUTHORIZED_REQUEST +
				MESSAGE_UNAUTHORIZED_REQUEST + TYPE_UNAUTHORIZED_RESOURCE_ACCESS);
	}
}
