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

    private static final int FIRST_OPERAND = 2;
    private static final int SECOND_OPERAND = 5;
    private static final BigDecimal SUM_RESULT = new BigDecimal(7);

    private final MathService mathService = new MathService();

    @Test
    public void whenSumShouldReturnSuccessfulResponse() {
        SumRequest sumRequest = SumRequest.builder()
                .firstOperand(new BigDecimal(FIRST_OPERAND))
                .secondOperand(new BigDecimal(SECOND_OPERAND))
                .build();

       BigDecimal result = mathService.sumValues(sumRequest);

       assertEquals(SUM_RESULT, result);
    }
}