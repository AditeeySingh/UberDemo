package com.aditeey.uber.repository;

import com.aditeey.uber.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends MongoRepository<Ride, String> {

    // For "my rides" (by passenger id)
    List<Ride> findByUserId(String userId);

    // All rides with a given status (REQUESTED, ACCEPTED, COMPLETED)
    List<Ride> findByStatus(String status);

    // Pending rides for drivers to see (status REQUESTED and no driver yet)
    List<Ride> findByStatusAndDriverIdIsNull(String status);

    // Rides for a particular driver
    List<Ride> findByDriverId(String driverId);
}