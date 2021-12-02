package com.tenpo.interview.service;

import com.tenpo.interview.model.request.SumRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test will test the Math service
 *
 * @author Agustin-Varela
 */
class MathServiceTest {

    private static final int VAL_1 = 2;
    private static final int VAL_2 = 5;
    private static final BigDecimal RESULT_1 = new BigDecimal(7);

    private final MathService mathService = new MathService();

    @Test
    public void whenSumShouldReturnSuccessfulResponse() {
        SumRequest sumRequest = SumRequest.builder()
                .value1(new BigDecimal(VAL_1))
                .value2(new BigDecimal(VAL_2))
                .build();

       BigDecimal result = mathService.sumValues(sumRequest);

       assertEquals(RESULT_1, result);
    }
}