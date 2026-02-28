package com.fajarcode.concert_ticket.ledger.service;


import com.fajarcode.concert_ticket.ledger.domain.TransactionLedger;
import com.fajarcode.concert_ticket.ledger.repository.TransactionLedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LedgerService {

    private final TransactionLedgerRepository repository;

    public void recordPayment(UUID bookingId, BigDecimal amount) {
        repository.save(
                TransactionLedger.payment(bookingId, amount)
        );
    }


    public List<TransactionLedger> getAllTransactions() {
        return repository.findAll();
    }

    public List<TransactionLedger> getByConcertId(UUID concertId) {
        return repository.findByConcertId(concertId);
    }

    public BigDecimal getTotalRevenueByConcert(UUID concertId) {
        return repository.sumAmountByConcertId(concertId)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal getTotalRevenue() {
        return repository.sumAllPayments()
                .orElse(BigDecimal.ZERO);
    }


    public long countAll() {
        return repository.count();
    }
}
