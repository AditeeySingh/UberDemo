package com.aditeey.uber.controller;

import com.aditeey.uber.model.Ride;
import com.aditeey.uber.service.RideQueryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rides")
public class RideQueryController {

    private final RideQueryService rideQueryService;

    public RideQueryController(RideQueryService rideQueryService) {
        this.rideQueryService = rideQueryService;
    }

    // Search rides by pickup OR drop location
    @GetMapping("/search")
    public List<Ride> searchRides(@RequestParam String text) {
        return rideQueryService.searchByKeyword(text);
    }

    // Filter rides by distance range
    @GetMapping("/filter-distance")
    public List<Ride> filterByDistance(
            @RequestParam Double min,
            @RequestParam Double max
    ) {
        return rideQueryService.filterByDistance(min, max);
    }

    // Filter rides by date range
    @GetMapping("/filter-date-range")
    public List<Ride> filterByDateRange(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate start,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate end
    ) {
        return rideQueryService.filterByDateRange(start, end);
    }

    // Sort rides by fare
    @GetMapping("/sort")
    public List<Ride> sortByFare(@RequestParam String order) {
        return rideQueryService.sortByFare(order);
    }
    // Get rides by user
    @GetMapping("/user/{userId}")
    public List<Ride> getRidesByUser(@PathVariable String userId) {
        return rideQueryService.findRidesByUser(userId);
    }
    // Get rides by user and status
    @GetMapping("/user/{userId}/status/{status}")
    public List<Ride> getRidesByUserAndStatus(
            @PathVariable String userId,
            @PathVariable String status
    ) {
        return rideQueryService.findRidesByUserAndStatus(userId, status);
    }
    //Advanced search (filter + sort + pagination)
    @GetMapping("/advanced-search")
    public Page<Ride> advancedSearch(
            @RequestParam String search,
            @RequestParam String status,
            @RequestParam String sortField,
            @RequestParam String order,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return rideQueryService.advancedSearch(
                search,
                status,
                sortField,
                order,
                page,
                size
        );
    }
}