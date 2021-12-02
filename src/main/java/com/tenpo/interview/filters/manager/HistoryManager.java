package com.tenpo.interview.filters.manager;

import com.tenpo.interview.model.HttpTraceHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

/**
 * This manager is used to manipulate the body in the request for call history.
 *
 * @author Agustin-Varela
 */
@Component
@RequestScope
@ConditionalOnProperty(prefix = "management.trace.http", name = "enabled", matchIfMissing = true)
@Slf4j
public class HistoryManager {

    private static final String COULD_NOT_READ_CACHED_REQUEST_BODY = "Could not read cached request body: {}";
    private static final String COULD_NOT_READ_CACHED_RESPONSE_BODY = "Could not read cached response body: {} ";

    private HttpTraceHistory httpTraceHistory;

    public HttpTraceHistory getHttpTraceHistory() {
        if (Objects.isNull(httpTraceHistory)) {
            httpTraceHistory = new HttpTraceHistory();
        }
        return httpTraceHistory;
    }

    public void updatePrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication)) {
            httpTraceHistory.setPrincipal(authentication);
        }
    }

    public void updateBody(ContentCachingRequestWrapper wrappedRequest, ContentCachingResponseWrapper wrappedResponse) {
        getHttpTraceHistory().setRequestBody(getRequestBody(wrappedRequest));
        getHttpTraceHistory().setResponseBody(getResponseBody(wrappedResponse));
    }

    protected String getRequestBody(ContentCachingRequestWrapper wrappedRequest) {
        String requestBody = null;
        if (wrappedRequest.getContentLength() > INTEGER_ZERO) {
            try {
                requestBody =  new String(wrappedRequest.getContentAsByteArray(), INTEGER_ZERO,
                        wrappedRequest.getContentLength(), wrappedRequest.getCharacterEncoding());

            } catch (UnsupportedEncodingException exception) {
                log.error(COULD_NOT_READ_CACHED_REQUEST_BODY, exception.getMessage(), exception);
            }
        }
        return requestBody;
    }

    protected String getResponseBody(ContentCachingResponseWrapper wrappedResponse) {
        String responseBody = null;
        if (wrappedResponse.getContentSize() > INTEGER_ZERO) {
            try {
                responseBody = new String(wrappedResponse.getContentAsByteArray(), INTEGER_ZERO,
                        wrappedResponse.getContentSize(), wrappedResponse.getCharacterEncoding());
            } catch (UnsupportedEncodingException exception) {
                log.error(COULD_NOT_READ_CACHED_RESPONSE_BODY, exception.getMessage(), exception);
            }
        }
        return responseBody;
    }
}
