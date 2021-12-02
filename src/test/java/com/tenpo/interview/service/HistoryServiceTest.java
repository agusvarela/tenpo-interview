package com.tenpo.interview.service;

import com.tenpo.interview.entities.HistoryEntity;
import com.tenpo.interview.repository.custom.HistoryCustomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This test will test the history service
 *
 * @author Agustin-Varela
 */
class HistoryServiceTest {

    @InjectMocks
    private HistoryService historyService;

    @Mock
    private HistoryCustomRepository historyCustomRepository;

    @Mock
    private HistoryEntity historyEntity;

    @Mock
    private Page<HistoryEntity> page;

    private static final int PAGE_NUMBER = 1;
    private static final int PAGE_SIZE = 6;
    private static final String SORT_BY = "id";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenRetrieveAllHistoryThenShouldBeSuccessfulResponse() {
        when(historyCustomRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(page.hasContent()).thenReturn(true);
        when(page.getContent()).thenReturn(List.of(historyEntity));

        List<HistoryEntity> results = historyService.retrieveAllHistory(PAGE_NUMBER, PAGE_SIZE, SORT_BY);

        assertEquals(PAGE_NUMBER, results.size());
        assertEquals(historyEntity, results.get(INTEGER_ZERO));

        verify(historyCustomRepository, times(INTEGER_ONE)).findAll(any(Pageable.class));
        verify(page, times(INTEGER_ONE)).hasContent();
        verify(page, times(INTEGER_ONE)).getContent();
    }

    @Test
    public void whenRetrieveAllHistoryThenShouldBeSuccessfulResponseWithEmptyPages() {
        when(historyCustomRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(page.hasContent()).thenReturn(true);
        when(page.getContent()).thenReturn(List.of());

        List<HistoryEntity> results = historyService.retrieveAllHistory(PAGE_NUMBER, PAGE_SIZE, SORT_BY);

        assertEquals(INTEGER_ZERO, results.size());

        verify(historyCustomRepository, times(INTEGER_ONE)).findAll(any(Pageable.class));
        verify(page, times(INTEGER_ONE)).hasContent();
        verify(page, times(INTEGER_ONE)).getContent();
    }
}