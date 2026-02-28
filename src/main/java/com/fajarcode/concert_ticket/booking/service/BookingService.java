package com.fajarcode.concert_ticket.booking.service;


import com.fajarcode.concert_ticket.booking.domain.Booking;
import com.fajarcode.concert_ticket.booking.domain.BookingStatus;
import com.fajarcode.concert_ticket.booking.dto.BookingResponse;
import com.fajarcode.concert_ticket.booking.dto.CreateBookingRequest;
import com.fajarcode.concert_ticket.booking.repository.BookingRepository;
import com.fajarcode.concert_ticket.common.exception.BusinessException;
import com.fajarcode.concert_ticket.common.exception.ResourceNotFoundException;
import com.fajarcode.concert_ticket.common.idempotency.service.IdempotencyService;
import com.fajarcode.concert_ticket.concert.domain.Concert;
import com.fajarcode.concert_ticket.concert.repository.ConcertRepository;
import com.fajarcode.concert_ticket.inventory.domain.TicketInventory;
import com.fajarcode.concert_ticket.inventory.repository.TicketInventoryRepository;
import com.fajarcode.concert_ticket.ledger.service.LedgerService;
import com.fajarcode.concert_ticket.pricing.service.PricingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final IdempotencyService idempotencyService;
    private final ConcertRepository concertRepository;
    private final LedgerService ledgerService;
    private final PricingService pricingService;
    private final TicketInventoryRepository ticketInventoryRepository;

    @Transactional
    public BookingResponse createBooking(
            CreateBookingRequest request,
            String idempotencyKey
    ) {

        // Idempotency check
        UUID existing = idempotencyService.check(idempotencyKey);
        if (existing != null) {
            return getBooking(existing);
        }

        // Lock inventory row
        TicketInventory inventory =
                ticketInventoryRepository
                        .lockInventory(
                                request.concertId(),
                                request.categoryId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Inventory not found"));

        // Validate stock
        if (inventory.getAvailableStock() < request.quantity()) {
            throw new BusinessException("Out of stock");
        }

        // Get concert base price
        Concert concert =
                concertRepository.findById(request.concertId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Concert not found"));

        // Calculate dynamic price
        BigDecimal totalPrice =
                pricingService.calculatePrice(
                        concert.getBasePrice(),
                        inventory.getTotalStock(),
                        inventory.getAvailableStock(),
                        request.quantity()
                );

        // Decrease stock (still inside locked transaction)
        inventory.decreaseStock(request.quantity());

        // Create booking
        Booking booking = Booking.createPending(
                request.userId(),
                request.concertId(),
                request.categoryId(),
                request.quantity(),
                totalPrice
        );

        bookingRepository.save(booking);

        // Append ledger entry (immutable)
        ledgerService.recordPayment(
                booking.getId(),
                totalPrice
        );

        // Save idempotency key
        idempotencyService.save(idempotencyKey, booking.getId());

        return BookingResponse.from(booking);
    }

    public BookingResponse getBooking(UUID bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        return BookingResponse.from(booking);
    }

    public List<BookingResponse> getUserBookings(UUID userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(BookingResponse::from)
                .toList();
    }

    @Transactional
    public void cancelBooking(UUID bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return;
        }

        booking.cancel();
    }

    @Transactional
    public void releaseExpiredBookings() {
        List<Booking> expired = bookingRepository
                .findByStatusAndExpiresAtBefore(
                        BookingStatus.PENDING,
                        Instant.now()
                );

        expired.forEach(Booking::cancel);
    }

    public long countAll() {
        return bookingRepository.count();
    }
}
