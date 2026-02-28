package com.fajarcode.concert_ticket.booking.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name = "booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "user_id", columnDefinition = "VARCHAR(36)")
    private UUID userId;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "concert_id", columnDefinition = "VARCHAR(36)")
    private UUID concertId;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "category_id", columnDefinition = "VARCHAR(36)")
    private UUID categoryId;

    private int quantity;

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private Instant expiresAt;
    private Instant createdAt;

    public static Booking createPending(
            UUID userId,
            UUID concertId,
            UUID categoryId,
            int quantity,
            BigDecimal totalPrice
    ) {
        return Booking.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .concertId(concertId)
                .categoryId(categoryId)
                .quantity(quantity)
                .totalPrice(totalPrice)
                .status(BookingStatus.PENDING)
                .expiresAt(Instant.now().plusSeconds(300))
                .createdAt(Instant.now())
                .build();
    }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
    }



}
