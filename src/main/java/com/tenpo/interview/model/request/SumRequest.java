package com.tenpo.interview.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * This object contains numbers.
 *
 * @author Agustin-Varela
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SumRequest {

    @NotNull(message = "First Operand is missing.")
    private BigDecimal firstOperand;

    @NotNull(message = "Second Operand is missing.")
    private BigDecimal secondOperand;

}
