package com.fajarcode.concert_ticket.analytic.controller;


import com.fajarcode.concert_ticket.analytic.dto.DashboardResponse;
import com.fajarcode.concert_ticket.analytic.service.AnalyticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticController {

    private final AnalyticService analyticsService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> dashboard() {
        return ResponseEntity.ok(
                analyticsService.getDashboard()
        );
    }



}
