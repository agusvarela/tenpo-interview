package com.tenpo.interview.rest;

import com.tenpo.interview.entities.HistoryEntity;
import com.tenpo.interview.exception.ApiError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.tenpo.interview.util.TenpoConstants.OK_MESSAGE;
import static com.tenpo.interview.util.TenpoConstants.UNAUTHORIZED_MESSAGE;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * This controller is used to see history.
 *
 * @author Agustin-Varela
 */
@Api(tags = "history")
@RequestMapping(value = "/history", produces = APPLICATION_JSON_VALUE)
@CrossOrigin
public interface HistoryController {

    /**
     * This endpoint will retrieve all system call history.
     *
     * @return the history entity list {@link HistoryEntity}
     */
    @ApiOperation(value = "All System Call History.", notes = "This endpoint will retrieve all system call " +
            "history from all endpoints paginated.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true,
            paramType = "header", dataTypeClass = String.class, example = "BEARER {token}")
    @ApiResponses(
            value = {
                    @ApiResponse(code = SC_OK, message = OK_MESSAGE, response = HistoryEntity.class),
                    @ApiResponse(code = SC_UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE, response = ApiError.class)
            })
    @GetMapping(value = "/all")
    ResponseEntity<List<HistoryEntity>> allHistory(@ApiParam(name = "pageNumber", defaultValue = "0")
                                                   @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                   @ApiParam(name = "pageSize", defaultValue = "10")
                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                   @ApiParam(name = "sortBy", defaultValue = "id",
                                                           allowableValues = "id, responseCode, endpoint, timeStamp")
                                                   @RequestParam(name = "sortBy", defaultValue = "id") String sortBy);
}
