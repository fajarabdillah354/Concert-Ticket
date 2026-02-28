package com.fajarcode.concert_ticket.booking.dto;

import com.fajarcode.concert_ticket.booking.domain.Booking;
import com.fajarcode.concert_ticket.booking.domain.BookingStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;



public record BookingResponse(

        UUID bookingId,
        UUID concertId,
        UUID categoryId,
        int quantity,
        BigDecimal totalPrice,
        BookingStatus status,
        Instant expiresAt,
        Instant createdAt

) {

    public static BookingResponse from(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getConcertId(),
                booking.getCategoryId(),
                booking.getQuantity(),
                booking.getTotalPrice(),
                booking.getStatus(),
                booking.getExpiresAt(),
                booking.getCreatedAt()
        );
    }


}
