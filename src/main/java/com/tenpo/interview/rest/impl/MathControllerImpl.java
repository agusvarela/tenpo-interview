package com.tenpo.interview.rest.impl;

import com.tenpo.interview.rest.MathController;
import com.tenpo.interview.service.MathService;
import com.tenpo.interview.model.request.SumRequest;
import com.tenpo.interview.model.response.SumResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Math operations service.
 *
 * @author Agustin-Varela
 */
@RestController
@AllArgsConstructor
@Slf4j
public class MathControllerImpl implements MathController {

    private static final String SUM_VALUES_LOG_MESSAGE = "Sum values: [{}],  [{}]";

    private final MathService mathService;

    @Override
    public ResponseEntity<SumResponse> sumNumbers(SumRequest sumRequest) {
        log.info(SUM_VALUES_LOG_MESSAGE, sumRequest.getFirstOperand(), sumRequest.getSecondOperand());

        return ResponseEntity.ok(SumResponse.builder()
                .sumResult(mathService.sumValues(sumRequest))
                .build());
    }
}
