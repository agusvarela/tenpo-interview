package com.tenpo.interview.repository.custom;

import com.tenpo.interview.entities.HistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;

import java.util.Collections;
import java.util.List;

/**
 * This custom repository is used to save the http request into the history.
 *
 * @author Agustin-Varela
 */
@RequiredArgsConstructor
public class CustomHistoryRepository implements HttpTraceRepository {

    @Autowired
    private HistoryCustomRepository historyCustomRepository;

    @Override
    public void add(HttpTrace httpTrace) {
        historyCustomRepository.save(
                HistoryEntity.builder()
                        .endpoint(httpTrace.getRequest().getUri().toString())
                        .responseCode(httpTrace.getResponse().getStatus())
                        .timeStamp(httpTrace.getTimestamp().toString())
                        .build()
        );
    }

    @Override
    public List<HttpTrace> findAll() {
        return Collections.emptyList();
    }
}
