package com.aditeey.uber.controller;

import com.aditeey.uber.service.AnalyticsService;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    // 17.1 — Rides per day
    @GetMapping("/rides-per-day")
    public List<Document> ridesPerDay() {
        return analyticsService.ridesPerDay();
    }

    // 17.2 — Driver summary
    @GetMapping("/driver/{driverId}/summary")
    public Document driverSummary(@PathVariable String driverId) {
        return analyticsService.driverSummary(driverId);
    }

    // 17.3 — Status summary
    @GetMapping("/status-summary")
    public List<Document> statusSummary() {
        return analyticsService.statusSummary();
    }
}