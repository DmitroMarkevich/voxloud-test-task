package com.voxloud.provisioning.errorhandling;

import lombok.Data;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class ErrorResponse {

    private Instant timestamp;

    private Integer statusCode;

    private String message;

    private List<?> details;
}