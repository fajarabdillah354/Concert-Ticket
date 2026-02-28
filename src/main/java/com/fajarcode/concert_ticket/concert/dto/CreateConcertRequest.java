package com.fajarcode.concert_ticket.concert.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record CreateConcertRequest(

        @NotBlank String name,
        @NotBlank String artist,
        @NotBlank String venue,

        @NotNull OffsetDateTime startTime,
        @NotNull OffsetDateTime endTime,

        @NotNull @Positive BigDecimal basePrice,
        @NotNull @Positive Integer capacity

) {
}
