package com.fajarcode.concert_ticket.common.exception;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            ResourceNotFoundException ex
    ) {
        return ResponseEntity.status(404)
                .body(new ApiErrorResponse(
                        "NOT_FOUND",
                        ex.getMessage(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusiness(
            BusinessException ex
    ) {
        return ResponseEntity.badRequest()
                .body(new ApiErrorResponse(
                        "BUSINESS_ERROR",
                        ex.getMessage(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(
            Exception ex
    ) {
        return ResponseEntity.internalServerError()
                .body(new ApiErrorResponse(
                        "INTERNAL_ERROR",
                        ex.getMessage(),
                        Instant.now()
                ));
    }


}
