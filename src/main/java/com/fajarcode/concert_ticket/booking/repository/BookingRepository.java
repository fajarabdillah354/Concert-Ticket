package com.fajarcode.concert_ticket.booking.repository;

import com.fajarcode.concert_ticket.booking.domain.Booking;
import com.fajarcode.concert_ticket.booking.domain.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


public interface BookingRepository extends JpaRepository<Booking, UUID> {

    List<Booking> findByUserId(UUID userId);

    List<Booking> findByStatusAndExpiresAtBefore(
            BookingStatus status,
            Instant now
    );


}
