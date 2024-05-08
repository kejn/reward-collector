package com.github.kejn.rewardcollector.api;

import java.time.LocalDateTime;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

/**
 * This is redundant, because SpringBoot already assures that there's global
 * exception handling implemented and stack-traces are disabled by default.
 * 
 * It's just example how you could handle some specific exception.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalErrorHandler {

	private static final String RESPONSE_CODE = "500";
	private static final String ERROR_MESSAGE = "Something went really wrong. Please contact support for further advice.";

	@ApiResponses({ //
			@ApiResponse(responseCode = RESPONSE_CODE, //
					description = ERROR_MESSAGE, //
					content = { @Content(schema = @Schema(implementation = ErrorResponse.class)) }) })
	@ExceptionHandler(NullPointerException.class)
	protected ResponseEntity<Object> handleNullPointerException(NullPointerException ex, HttpServletRequest request)
			throws Exception {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		var body = new ErrorResponse(ERROR_MESSAGE, status.value(), request.getRequestURI());
		return ResponseEntity.status(status).body(body);
	}

	private static record ErrorResponse(String error, int status, String path, LocalDateTime date) {
		public ErrorResponse(String error, int status, String path) {
			this(error, status, path, LocalDateTime.now());
		}
	}
}
