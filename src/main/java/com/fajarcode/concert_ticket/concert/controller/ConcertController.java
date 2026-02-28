package com.fajarcode.concert_ticket.concert.controller;



import com.fajarcode.concert_ticket.concert.dto.ConcertResponse;
import com.fajarcode.concert_ticket.concert.dto.CreateConcertRequest;
import com.fajarcode.concert_ticket.concert.dto.UpdateConcertRequest;
import com.fajarcode.concert_ticket.concert.service.ConcertService;
import com.fajarcode.concert_ticket.inventory.service.TicketInventoryService;
import com.fajarcode.concert_ticket.ledger.service.LedgerService;
import com.fajarcode.concert_ticket.pricing.service.PricingFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/concerts")
@RequiredArgsConstructor
public class ConcertController {

    private final PricingFacadeService pricingFacadeService;
    private final TicketInventoryService inventoryService;
    private final ConcertService concertService;
    private final LedgerService ledgerService;


    // Settlement
    @GetMapping("/{id}/settlement")
    public ResponseEntity<BigDecimal> getSettlement(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                ledgerService.getTotalRevenueByConcert(id)
        );
    }




    // ========================
    // PRICING & AVAILABILITY
    // ========================

    @GetMapping("/{concertId}/price")
    public ResponseEntity<BigDecimal> getPrice(
            @PathVariable UUID concertId,
            @RequestParam UUID categoryId,
            @RequestParam int quantity
    ) {
        return ResponseEntity.ok(
                pricingFacadeService.getFinalPrice(
                        concertId,
                        categoryId,
                        quantity
                )
        );
    }

    @GetMapping("/{concertId}/availability")
    public ResponseEntity<Integer> getAvailability(
            @PathVariable UUID concertId,
            @RequestParam UUID categoryId
    ) {
        return ResponseEntity.ok(
                inventoryService.getAvailableTickets(
                        concertId,
                        categoryId
                )
        );
    }

    // ========================
    // CRUD CONCERT
    // ========================

    // CREATE
    @PostMapping
    public ResponseEntity<ConcertResponse> create(
            @RequestBody CreateConcertRequest request
    ) {
        ConcertResponse response = concertService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ConcertResponse> getById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(concertService.getById(id));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<ConcertResponse>> listAll() {
        return ResponseEntity.ok(concertService.listAll());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ConcertResponse> update(
            @PathVariable UUID id,
            @RequestBody UpdateConcertRequest request
    ) {
        return ResponseEntity.ok(concertService.update(id, request));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id
    ) {
        concertService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
