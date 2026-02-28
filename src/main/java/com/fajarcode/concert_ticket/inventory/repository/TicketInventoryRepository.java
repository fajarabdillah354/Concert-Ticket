package com.fajarcode.concert_ticket.inventory.repository;

import com.fajarcode.concert_ticket.inventory.domain.TicketInventory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.*;
import jakarta.persistence.LockModeType;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface TicketInventoryRepository extends JpaRepository<TicketInventory, UUID>{

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
       SELECT i FROM TicketInventory i
       WHERE i.concertId = :concertId
       AND i.categoryId = :categoryId
    """)
    Optional<TicketInventory> lockInventory(
            @Param("concertId") UUID concertId,
            @Param("categoryId") UUID categoryId
    );

    @Query("""
   SELECT ti.availableStock
   FROM TicketInventory ti
   WHERE ti.concertId = :concertId
   AND ti.categoryId = :categoryId
    """)
    Optional<Integer> findAvailableSeats(
            @Param("concertId") UUID concertId,
            @Param("categoryId") UUID categoryId
    );

    Optional<TicketInventory> findByConcertId(UUID concertId);


    Optional<TicketInventory> findByConcertIdAndCategoryId(
            UUID concertId,
            UUID categoryId
    );

}
