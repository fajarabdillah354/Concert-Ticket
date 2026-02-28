package com.fajarcode.concert_ticket.pricing.service;


import java.math.BigDecimal;


public interface PricingService {

    BigDecimal calculatePrice(
            BigDecimal basePrice,
            int totalStock,
            int availableStock,
            int quantity
    );

}
