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

    @NotNull(message = "Value 1 is missing.")
    private BigDecimal value1;

    @NotNull(message = "Value 2 is missing.")
    private BigDecimal value2;

}
