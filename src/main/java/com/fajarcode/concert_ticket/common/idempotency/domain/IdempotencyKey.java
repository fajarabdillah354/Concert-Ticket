package com.fajarcode.concert_ticket.common.idempotency.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;



@Entity
@Table(name = "idempotency_keys")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdempotencyKey {

    @Id
    @Column(name = "idempotency_key", nullable = false, updatable = false)
    private String idempotencyKey;

    @Column(name = "booking_id", nullable = false)
    private UUID bookingId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;


}
