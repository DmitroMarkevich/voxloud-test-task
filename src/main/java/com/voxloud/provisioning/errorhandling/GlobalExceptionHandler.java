package com.voxloud.provisioning.errorhandling;

import com.voxloud.provisioning.errorhandling.exception.InvalidMacAddressException;
import com.voxloud.provisioning.errorhandling.exception.JsonSerializationException;
import com.voxloud.provisioning.errorhandling.exception.UnsupportedDeviceModelException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.voxloud.provisioning.errorhandling.exception.DeviceNotFoundException;

/**
 * Global exception handler for the application.
 * This class is responsible for handling exceptions thrown by the controllers
 * and returning appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DeviceNotFoundException.class)
    private ResponseEntity<Object> handleException(DeviceNotFoundException exception) {
        return ErrorResponseBuilder.buildExceptionBody(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidMacAddressException.class)
    private ResponseEntity<Object> handleException(InvalidMacAddressException exception) {
        return ErrorResponseBuilder.buildExceptionBody(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedDeviceModelException.class)
    private ResponseEntity<Object> handleException(UnsupportedDeviceModelException exception) {
        return ErrorResponseBuilder.buildExceptionBody(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonSerializationException.class)
    private ResponseEntity<Object> handleException(JsonSerializationException exception) {
        return ErrorResponseBuilder.buildExceptionBody(exception, HttpStatus.BAD_REQUEST);
    }
}