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

    private static final int VAL_1 = 2;
    private static final int VAL_2 = 5;
    private static final BigDecimal RESULT_1 = new BigDecimal(7);

    @BeforeEach
    public void setup() {
        mathController = new MathControllerImpl(mathService);
    }

    @Test
    public void whenSumNumbersShouldReturnSuccessfulResponse() {
        SumRequest sumRequest = SumRequest.builder()
                .value1(new BigDecimal(VAL_1))
                .value2(new BigDecimal(VAL_2))
                .build();

        ResponseEntity<SumResponse> result = mathController.sumNumbers(sumRequest);

        assertNotNull(result.getBody());
        assertEquals(RESULT_1, result.getBody().getSumResult());
    }
}