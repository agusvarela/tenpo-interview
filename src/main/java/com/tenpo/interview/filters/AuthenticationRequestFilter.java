package com.tenpo.interview.filters;

import com.tenpo.interview.service.JWTService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * This filter is used to authenticate requests.
 *
 * @author Agustin-Varela
 */
@Component
@AllArgsConstructor
public class AuthenticationRequestFilter extends OncePerRequestFilter {

	private static final int BEGIN_INDEX = 7;
	private static final String BEARER = "BEARER ";
	private static final String AUTHORIZATION = "Authorization";
	private static final String UNABLE_TO_GET_JWT_TOKEN_OR_JWT_TOKEN_HAS_EXPIRED_LOG_MESSAGE =
			"Unable to get JWT Token or JWT Token has expired";

	private final JWTService jwtTokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {

		getJwtFromRequest(request).ifPresent(token -> {
			try {
				if (jwtTokenService.validateToken(token)) {
					setSecurityContext(new WebAuthenticationDetailsSource().buildDetails(request), token);
				}
			} catch (IllegalArgumentException | MalformedJwtException | ExpiredJwtException e) {
				logger.error(UNABLE_TO_GET_JWT_TOKEN_OR_JWT_TOKEN_HAS_EXPIRED_LOG_MESSAGE);
			}
		});

		filterChain.doFilter(request, response);
	}

	private void setSecurityContext(WebAuthenticationDetails authDetails, String token) {
		final UserDetails userDetails = new User(jwtTokenService.getUsernameFromToken(token), EMPTY,
				jwtTokenService.getRoles(token).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

		final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
				null, userDetails.getAuthorities());

		authentication.setDetails(authDetails);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private static Optional<String> getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION);

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
			return Optional.of(bearerToken.substring(BEGIN_INDEX));
		}
		return Optional.empty();
	}
}