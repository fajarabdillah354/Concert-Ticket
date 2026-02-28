package com.fajarcode.concert_ticket.analytic.service;


import com.fajarcode.concert_ticket.analytic.dto.DashboardResponse;
import com.fajarcode.concert_ticket.booking.service.BookingService;
import com.fajarcode.concert_ticket.concert.service.ConcertService;
import com.fajarcode.concert_ticket.ledger.service.LedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticService {

    private final LedgerService ledgerService;
    private final BookingService bookingService;
    private final ConcertService concertService;

    public DashboardResponse getDashboard() {
        return DashboardResponse.builder()
                .totalRevenue(ledgerService.getTotalRevenue())
                .totalTransactions(ledgerService.countAll())
                .totalBookings(bookingService.countAll())
                .totalConcerts(concertService.countAll())
                .build();
    }


}
