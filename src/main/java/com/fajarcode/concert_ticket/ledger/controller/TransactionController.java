package com.fajarcode.concert_ticket.ledger.controller;


import com.fajarcode.concert_ticket.ledger.domain.TransactionLedger;
import com.fajarcode.concert_ticket.ledger.service.LedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final LedgerService ledgerService;

    @GetMapping
    public ResponseEntity<List<TransactionLedger>> getAll() {
        return ResponseEntity.ok(
                ledgerService.getAllTransactions()
        );
    }


}
