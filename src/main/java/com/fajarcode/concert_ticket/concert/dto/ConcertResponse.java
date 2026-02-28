package com.fajarcode.concert_ticket.concert.dto;


import com.fajarcode.concert_ticket.concert.domain.Concert;
import com.fajarcode.concert_ticket.concert.domain.ConcertStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ConcertResponse(

        UUID id,
        String name,
        String artist,
        String venue,
        OffsetDateTime startTime,
        OffsetDateTime endTime,
        BigDecimal basePrice,
        Integer capacity,
        ConcertStatus status


) {

    public static ConcertResponse from(Concert concert) {
        return new ConcertResponse(
                concert.getId(),
                concert.getName(),
                concert.getArtist(),
                concert.getVenue(),
                concert.getStartTime(),
                concert.getEndTime(),
                concert.getBasePrice(),
                concert.getCapacity(),
                concert.getStatus()
        );
    }


}
