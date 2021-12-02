package com.tenpo.interview.rest.impl;

import com.tenpo.interview.entities.HistoryEntity;
import com.tenpo.interview.rest.HistoryController;
import com.tenpo.interview.service.HistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Retrieve a history.
 *
 * @author Agustin-Varela
 */
@RestController
@AllArgsConstructor
@Slf4j
public class HistoryControllerImpl implements HistoryController {

    private final HistoryService historyService;

    @Override
    public ResponseEntity<List<HistoryEntity>> allHistory(Integer pageNumber, Integer pageSize, String sortBy) {
        return ResponseEntity.ok(historyService.retrieveAllHistory(pageNumber, pageSize, sortBy));
    }
}

