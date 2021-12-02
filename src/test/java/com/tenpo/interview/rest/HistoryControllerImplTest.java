package com.tenpo.interview.rest;

import com.tenpo.interview.entities.HistoryEntity;
import com.tenpo.interview.rest.impl.HistoryControllerImpl;
import com.tenpo.interview.service.HistoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This test will test the history controller impl.
 *
 * @author Agustin-Varela
 */
class HistoryControllerImplTest {

    @InjectMocks
    private HistoryControllerImpl historyController;

    @Mock
    private HistoryService historyService;

    @Mock
    private HistoryEntity historyEntity;

    private static final int PAGE_NUMBER = 1;
    private static final int PAGE_SIZE = 6;
    private static final String SORT_BY = "id";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenRetrieveAllHistoryThenShouldBeSuccessfulResponse() {
        when(historyService.retrieveAllHistory(PAGE_NUMBER, PAGE_SIZE, SORT_BY))
                .thenReturn(List.of(historyEntity));

        ResponseEntity<List<HistoryEntity>> results = historyController.allHistory(PAGE_NUMBER, PAGE_SIZE, SORT_BY);

        assertTrue(CollectionUtils.isNotEmpty(results.getBody()));
        assertEquals(PAGE_NUMBER, results.getBody().size());
        assertEquals(historyEntity, results.getBody().get(INTEGER_ZERO));

        verify(historyService, times(INTEGER_ONE)).retrieveAllHistory(PAGE_NUMBER, PAGE_SIZE, SORT_BY);
    }

    @Test
    public void whenRetrieveAllHistoryThenShouldBeSuccessfulResponseWithEmptyPages() {
        when(historyService.retrieveAllHistory(PAGE_NUMBER, PAGE_SIZE, SORT_BY))
                .thenReturn(List.of());

        ResponseEntity<List<HistoryEntity>> results = historyController.allHistory(PAGE_NUMBER, PAGE_SIZE, SORT_BY);

        assertTrue(CollectionUtils.isEmpty(results.getBody()));
        assertEquals(INTEGER_ZERO, results.getBody().size());

        verify(historyService, times(INTEGER_ONE)).retrieveAllHistory(PAGE_NUMBER, PAGE_SIZE, SORT_BY);
    }
}