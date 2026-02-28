package com.fajarcode.concert_ticket.inventory.service;


import com.fajarcode.concert_ticket.inventory.domain.TicketInventory;
import com.fajarcode.concert_ticket.inventory.repository.TicketInventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketInventoryService {


    private final TicketInventoryRepository repository;

    @Cacheable(
            value = "concert-availability",
            key = "#concertId + '-' + #categoryId",
            unless = "#result == null"
    )
    public Integer getAvailableTickets(
            UUID concertId,
            UUID categoryId
    ) {

        System.out.println("concertId = " + concertId);
        System.out.println("categoryId = " + categoryId);

        List<TicketInventory> all = repository.findAll();
        System.out.println("ALL DATA SIZE = " + all.size());

        all.forEach(i -> {
            System.out.println("DB concertId=" + i.getConcertId());
            System.out.println("DB categoryId=" + i.getCategoryId());
        });

        TicketInventory inventory =
                repository
                        .findByConcertIdAndCategoryId(concertId, categoryId)
                        .orElseThrow(() -> new RuntimeException("Inventory not found"));

        return inventory.getAvailableStock();



//        System.out.println("Fetching availability from DB...");
//
//        TicketInventory inventory =
//                repository
//                        .findByConcertIdAndCategoryId(
//                                concertId,
//                                categoryId
//                        )
//                        .orElseThrow(() -> new RuntimeException("Inventory not found"));
//
//        return inventory.getAvailableStock();
    }

    @CacheEvict(
            value = "concert-availability",
            key = "#concertId + '-' + #categoryId"
    )
    public void evictAvailability(
            UUID concertId,
            UUID categoryId
    ) {
        // will called after booking successfyly
    }


    @Transactional
    public void reserve(UUID concertId, UUID categoryId, int qty) {

        TicketInventory inventory = repository
                .lockInventory(concertId, categoryId)
                .orElseThrow();

        inventory.decreaseStock(qty);
    }





}
