package com.fajarcode.concert_ticket.concert.repository;


import com.fajarcode.concert_ticket.concert.domain.Concert;
import com.fajarcode.concert_ticket.concert.domain.ConcertStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;


public interface ConcertRepository extends JpaRepository<Concert, UUID> {

    List<Concert> findByStatus(ConcertStatus status);

    List<Concert> findByArtistContainingIgnoreCase(String artist);

    List<Concert> findByNameContainingIgnoreCase(String name);

    List<Concert> findByStartTimeBetween(
            OffsetDateTime start,
            OffsetDateTime end
    );

}
