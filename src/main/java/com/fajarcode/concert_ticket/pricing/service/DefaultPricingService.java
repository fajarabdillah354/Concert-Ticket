package com.fajarcode.concert_ticket.pricing.service;




import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class DefaultPricingService implements PricingService {
    @Value("${app.pricing.min-multiplier}")
    private double minMultiplier;

    @Value("${app.pricing.max-multiplier}")
    private double maxMultiplier;

    @Override
    public BigDecimal calculatePrice(
            BigDecimal basePrice,
            int totalStock,
            int availableStock,
            int quantity
    ) {

        double availabilityRatio =
                (double) availableStock / totalStock;

        double demandMultiplier =
                (1 - availabilityRatio) * maxMultiplier;

        demandMultiplier = Math.max(minMultiplier,
                Math.min(maxMultiplier, demandMultiplier));

        BigDecimal finalPricePerTicket =
                basePrice.multiply(
                        BigDecimal.valueOf(1 + demandMultiplier)
                );

        return finalPricePerTicket
                .multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_UP);

    }

}
