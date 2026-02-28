package com.fajarcode.concert_ticket.booking.controller;


import com.fajarcode.concert_ticket.booking.dto.BookingResponse;
import com.fajarcode.concert_ticket.booking.dto.CreateBookingRequest;
import com.fajarcode.concert_ticket.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
//@Transactional add this annotation for testing with H2 for handle no transaction
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> create(
            @Valid @RequestBody CreateBookingRequest request,
            @RequestHeader("Idempotency-Key") String idempotencyKey
    ) {
        return ResponseEntity.ok(
                bookingService.createBooking(request, idempotencyKey)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                bookingService.getBooking(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getUserBookings(
            @RequestParam UUID userId
    ) {
        return ResponseEntity.ok(
                bookingService.getUserBookings(userId)
        );
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(
            @PathVariable UUID id
    ) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

    // ============================
    // RELEASE EXPIRED BOOKINGS
    // ============================

    @PostMapping("/release-expired")
    public ResponseEntity<Void> releaseExpired() {
        bookingService.releaseExpiredBookings();
        return ResponseEntity.noContent().build();
    }

}
