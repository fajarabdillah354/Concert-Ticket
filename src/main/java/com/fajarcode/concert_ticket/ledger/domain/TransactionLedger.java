package com.fajarcode.concert_ticket.ledger.domain;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionLedger {

    @Id
    private UUID id;

    private UUID bookingId;

    private String type; // PAYMENT / REFUND

    private BigDecimal amount;

    private String status;

    private Instant createdAt;

    public static TransactionLedger payment(UUID bookingId, BigDecimal amount) {
        return TransactionLedger.builder()
                .id(UUID.randomUUID())
                .bookingId(bookingId)
                .type("PAYMENT")
                .amount(amount)
                .status("SUCCESS")
                .createdAt(Instant.now())
                .build();
    }


}
