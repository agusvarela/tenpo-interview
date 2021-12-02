package com.tenpo.interview.service;

import com.tenpo.interview.model.request.SumRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * This service will process all related to the math operations.
 *
 * @author Agustin-Varela
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MathService {

    /**
     * This method will sum two arguments.
     *
     * @param sumRequest Represents the arguments.
     * @return the result
     */
    public BigDecimal sumValues(SumRequest sumRequest) {
        return sumRequest.getValue1().add(sumRequest.getValue2());
    }
}
