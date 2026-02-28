package com.fajarcode.concert_ticket.common.idempotency.service;



import com.fajarcode.concert_ticket.common.idempotency.domain.IdempotencyKey;
import com.fajarcode.concert_ticket.common.idempotency.repository.IdempotencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IdempotencyService {


    private final IdempotencyRepository repository;

    public UUID check(String key) {
        return repository.findById(key)
                .map(IdempotencyKey::getBookingId)
                .orElse(null);
    }

    public void save(String key, UUID bookingId) {
        repository.save(
                IdempotencyKey.builder()
                        .idempotencyKey(key)
                        .bookingId(bookingId)
                        .createdAt(Instant.now())
                        .build()
        );
    }

}
