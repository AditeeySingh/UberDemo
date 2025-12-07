package com.aditeey.uber.controller;

import com.aditeey.uber.model.Ride;
import com.aditeey.uber.repository.RideRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/rides")
public class RideController {

    private final RideRepository rideRepository;

    public RideController(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    // USER CREATES RIDE
    @PostMapping
    public Ride createRide(@RequestBody Ride ride, Authentication auth) {
        ride.setUserId(auth.getName());
        ride.setStatus("REQUESTED");
        ride.setCreatedAt(new Date());
        return rideRepository.save(ride);
    }

    // DRIVER or USER COMPLETES RIDE
    @PostMapping("/{id}/complete")
    public Ride completeRide(@PathVariable String id) {

        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (!ride.getStatus().equals("ACCEPTED")) {
            throw new RuntimeException("Ride is not ACCEPTED yet");
        }

        ride.setStatus("COMPLETED");
        return rideRepository.save(ride);
    }
}