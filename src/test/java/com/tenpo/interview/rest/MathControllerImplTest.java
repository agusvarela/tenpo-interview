package com.tenpo.interview.rest;

import com.tenpo.interview.model.request.SumRequest;
import com.tenpo.interview.model.response.SumResponse;
import com.tenpo.interview.rest.impl.MathControllerImpl;
import com.tenpo.interview.service.MathService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This test will test the math controller impl test.
 *
 * @author Agustin-Varela
 */
class MathControllerImplTest {

    private MathControllerImpl mathController;

    private final MathService mathService = new MathService();

    private static final int FIRST_OPERAND = 2;
    private static final int SECOND_OPERAND = 5;
    private static final BigDecimal SUM_RESULT = new BigDecimal(7);

    @BeforeEach
    public void setup() {
        mathController = new MathControllerImpl(mathService);
    }

    @Test
    public void whenSumNumbersShouldReturnSuccessfulResponse() {
        SumRequest sumRequest = SumRequest.builder()
                .firstOperand(new BigDecimal(FIRST_OPERAND))
                .secondOperand(new BigDecimal(SECOND_OPERAND))
                .build();

        ResponseEntity<SumResponse> result = mathController.sumNumbers(sumRequest);

        assertNotNull(result.getBody());
        assertEquals(SUM_RESULT, result.getBody().getSumResult());
    }
}