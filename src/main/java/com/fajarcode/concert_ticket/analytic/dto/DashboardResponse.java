package com.fajarcode.concert_ticket.analytic.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DashboardResponse {

    private BigDecimal totalRevenue;
    private long totalTransactions;
    private long totalBookings;
    private long totalConcerts;

}
