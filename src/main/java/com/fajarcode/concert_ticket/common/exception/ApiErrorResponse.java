package com.fajarcode.concert_ticket.common.exception;

import java.time.Instant;


public record ApiErrorResponse(

        String errorCode,
        String message,
        Instant timestamp

) {
}
