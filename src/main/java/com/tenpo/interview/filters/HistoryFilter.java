package com.tenpo.interview.filters;

import com.tenpo.interview.filters.manager.HistoryManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * This filter is used to save all the requests history.
 *
 * @author Agustin-Varela
 */
@Component
@ConditionalOnProperty(prefix = "management.trace.http", name = "enabled", matchIfMissing = true)
@Slf4j
public class HistoryFilter extends OncePerRequestFilter {

    private static final String COULD_NOT_READ_THE_REQUEST_URL = "Could not read the request URL: {}";

    @Value("${management.trace.http.tracebody:false}")
    private boolean traceBody;

    private final HistoryManager historyManager;

    public HistoryFilter(HistoryManager historyManager) {
        super();
        this.historyManager = historyManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!isRequestValid(request) || !traceBody) {
            filterChain.doFilter(request, response);
            return;
        }

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request, 1000);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
            historyManager.updateBody(wrappedRequest, wrappedResponse);
        } finally {
            wrappedResponse.copyBodyToResponse();
        }
    }

    private boolean isRequestValid(HttpServletRequest request) {
        boolean isValid = false;
        try {
            new URI(request.getRequestURL().toString());
            isValid = true;
        } catch (URISyntaxException exception) {
            log.error(COULD_NOT_READ_THE_REQUEST_URL, exception.getMessage(), exception);
        }
        return isValid;
    }
}
