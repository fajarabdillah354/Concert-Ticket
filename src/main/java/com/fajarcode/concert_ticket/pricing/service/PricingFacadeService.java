package com.fajarcode.concert_ticket.pricing.service;


import com.fajarcode.concert_ticket.concert.domain.Concert;
import com.fajarcode.concert_ticket.concert.repository.ConcertRepository;
import com.fajarcode.concert_ticket.inventory.domain.TicketInventory;
import com.fajarcode.concert_ticket.inventory.repository.TicketInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PricingFacadeService {


    private final ConcertRepository concertRepository;
    private final TicketInventoryRepository inventoryRepository;
    private final PricingService pricingService;

    @Cacheable(
            value = "concert-pricing",
            key = "#concertId + '-' + #categoryId + '-' + #quantity",
            unless = "#result == null"
    )
    public BigDecimal getFinalPrice(
            UUID concertId,
            UUID categoryId,
            int quantity
    ) {

        Concert concert =
                concertRepository.findById(concertId)
                        .orElseThrow();

        TicketInventory inventory =
                inventoryRepository
                        .findByConcertIdAndCategoryId(concertId, categoryId)
                        .orElseThrow();

        return pricingService.calculatePrice(
                concert.getBasePrice(),
                inventory.getTotalStock(),
                inventory.getAvailableStock(),
                quantity
        );
    }



}
