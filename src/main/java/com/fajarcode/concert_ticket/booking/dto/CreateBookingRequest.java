package com.fajarcode.concert_ticket.booking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;


public record CreateBookingRequest(

        @NotNull UUID userId,
        @NotNull UUID concertId,
        @NotNull UUID categoryId,

        @Min(1)
        int quantity

) {
}
