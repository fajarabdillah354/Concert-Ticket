package com.fajarcode.concert_ticket.ledger.repository;

import com.fajarcode.concert_ticket.ledger.domain.TransactionLedger;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface TransactionLedgerRepository extends JpaRepository<TransactionLedger, UUID>{

    List<TransactionLedger> findByConcertId(UUID concertId);

    // Total revenue per concert
    @Query("""
        SELECT SUM(t.amount)
        FROM TransactionLedger t
        WHERE t.concertId = :concertId
          AND t.type = 'PAYMENT'
    """)
    Optional<BigDecimal> sumAmountByConcertId(@Param("concertId") UUID concertId);

    // Total all payment
    @Query("""
        SELECT SUM(t.amount)
        FROM TransactionLedger t
        WHERE t.type = 'PAYMENT'
    """)
    Optional<BigDecimal> sumAllPayments();
}
