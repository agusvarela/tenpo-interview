package com.tenpo.interview.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * This object contains the result of the sum.
 *
 * @author Agustin-Varela
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SumResponse {

    private BigDecimal sumResult;

}
