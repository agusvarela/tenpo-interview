package com.tenpo.interview.repository.custom;

import com.tenpo.interview.entities.HistoryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * This repository is used to history calls.
 *
 * @author Agustin-Varela
 */
public interface HistoryCustomRepository extends PagingAndSortingRepository<HistoryEntity, Long> {
}
