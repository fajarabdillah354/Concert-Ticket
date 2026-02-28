package com.fajarcode.concert_ticket.concert.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;


@Entity
@Table(name = "concert")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Concert {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "VARCHAR(36)")
    private UUID id;

    private String name;
    private String artist;
    private String venue;

    private OffsetDateTime startTime;
    private OffsetDateTime endTime;

    private BigDecimal basePrice;

    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private ConcertStatus status;

    private OffsetDateTime createdAt;

    public static Concert create(
            String name,
            String artist,
            String venue,
            OffsetDateTime startTime,
            OffsetDateTime endTime,
            BigDecimal basePrice,
            Integer capacity
    ) {
        return Concert.builder()
                .id(UUID.randomUUID())
                .name(name)
                .artist(artist)
                .venue(venue)
                .startTime(startTime)
                .endTime(endTime)
                .basePrice(basePrice)
                .capacity(capacity)
                .status(ConcertStatus.UPCOMING)
                .createdAt(OffsetDateTime.now())
                .build();
    }

    public void update(
            String name,
            String artist,
            String venue,
            OffsetDateTime startTime,
            OffsetDateTime endTime,
            BigDecimal basePrice,
            Integer capacity
    ) {
        this.name = name;
        this.artist = artist;
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.basePrice = basePrice;
        this.capacity = capacity;
    }


}
