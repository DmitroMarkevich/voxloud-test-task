package com.voxloud.provisioning.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Collections;

public class ErrorResponseBuilder {

    /**
     * Creates a standardized error response entity for the given exception.
     *
     * @param httpStatus The HTTP status code to be included in the response.
     * @return A {@link ResponseEntity} containing the error response body with
     * timestamp, status code, message, and details about the exception.
     */
    public static ResponseEntity<Object> buildExceptionBody(Exception exception, HttpStatus httpStatus) {

        return ResponseEntity.status(httpStatus)
                .body(ErrorResponse.builder()
                        .statusCode(httpStatus.value())
                        .timestamp(Instant.now())
                        .message(httpStatus.getReasonPhrase())
                        .details(Collections.singletonList(exception.getMessage()))
                        .build()
                );
    }
}