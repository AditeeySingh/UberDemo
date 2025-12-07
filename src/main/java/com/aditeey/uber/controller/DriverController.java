package com.aditeey.uber.controller;

import com.aditeey.uber.model.Ride;
import com.aditeey.uber.repository.RideRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/driver")
public class DriverController {

    private final RideRepository rideRepository;

    public DriverController(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    // SEE ALL PENDING RIDES
    @GetMapping("/rides/requests")
    public List<Ride> getPendingRides() {
        return rideRepository.findByStatus("REQUESTED");
    }

    //DRIVER ACCEPTS A RIDE
    @PostMapping("/rides/{rideId}/accept")
    public Ride acceptRide(@PathVariable String rideId, Authentication authentication) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (!ride.getStatus().equals("REQUESTED")) {
            throw new RuntimeException("Ride already accepted or completed");
        }

        String driverUsername = authentication.getName();

        ride.setDriverId(driverUsername);
        ride.setStatus("ACCEPTED");

        return rideRepository.save(ride);
    }
}