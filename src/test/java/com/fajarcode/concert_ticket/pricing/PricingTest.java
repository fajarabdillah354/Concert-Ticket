package com.fajarcode.concert_ticket.pricing;


import com.fajarcode.concert_ticket.pricing.service.DefaultPricingService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;



class PricingTest {

    private final DefaultPricingService pricingService =
            new DefaultPricingService();

    @Test
    void shouldIncreasePriceWhenLowStock() {

        BigDecimal result =
                pricingService.calculatePrice(
                        BigDecimal.valueOf(100),
                        100,
                        5,
                        1
                );

        assertThat(result).isGreaterThan(BigDecimal.valueOf(100));
    }

}
