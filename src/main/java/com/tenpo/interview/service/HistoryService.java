package com.tenpo.interview.service;

import com.tenpo.interview.entities.HistoryEntity;
import com.tenpo.interview.repository.custom.HistoryCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * This service will process all related to history.
 *
 * @author Agustin-Varela
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryService {

    private final HistoryCustomRepository historyCustomRepository;

    /**
     * This method will retrieve call history paginated.
     *
     * @param pageNumber    Represents the number of the page.
     * @param pageSize      Represents the size of the page.
     * @param sortBy        Indicates which criteria will be used to paginate.
     * @return the history paginated
     */
    public List<HistoryEntity> retrieveAllHistory(Integer pageNumber, Integer pageSize, String sortBy) {
        Page<HistoryEntity> historyResult = historyCustomRepository
                .findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)));

        return historyResult.hasContent() ? historyResult.getContent() : Collections.emptyList();
    }
}
