package com.aditeey.uber.service;

import com.aditeey.uber.model.Ride;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.time.LocalDate;

@Service
public class RideQueryService {

    private final MongoTemplate mongoTemplate;

    public RideQueryService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    // Keyword Search (OR + Regex)

    private Criteria keywordCriteria(String text) {
        return new Criteria().orOperator(
                Criteria.where("pickupLocation").regex(text, "i"),
                Criteria.where("dropLocation").regex(text, "i")
        );
    }

    public List<Ride> searchByKeyword(String text) {
        Query query = new Query(keywordCriteria(text));
        return mongoTemplate.find(query, Ride.class);
    }

    // Distance Range Filter (gte + lte)

    private Criteria distanceRangeCriteria(Double min, Double max) {
        return Criteria.where("distanceKm").gte(min).lte(max);
    }

    public List<Ride> filterByDistance(Double min, Double max) {
        Query query = new Query(distanceRangeCriteria(min, max));
        return mongoTemplate.find(query, Ride.class);
    }

    // Date Range Filter (LocalDate + AND)

    private Criteria dateRangeCriteria(LocalDate start, LocalDate end) {
        return Criteria.where("createdDate").gte(start).lte(end);
    }

    public List<Ride> filterByDateRange(LocalDate start, LocalDate end) {
        Query query = new Query(dateRangeCriteria(start, end));
        return mongoTemplate.find(query, Ride.class);
    }

    //Sorting by Fare (ASC / DESC)

    public List<Ride> sortByFare(String order) {
        Query query = new Query();

        Sort.Direction direction =
                order.equalsIgnoreCase("asc")
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC;

        query.with(Sort.by(direction, "fareAmount"));
        return mongoTemplate.find(query, Ride.class);
    }

    //Combine Filters (Status + Keyword)
    private Criteria statusCriteria(String status) {
        return Criteria.where("status").is(status);
    }
    public List<Ride> filterByStatusAndKeyword(String status, String text) {
        Criteria criteria = new Criteria().andOperator(
                statusCriteria(status),
                keywordCriteria(text)
        );

        Query query = new Query(criteria);
        return mongoTemplate.find(query, Ride.class);
    }

    //Advanced Search (Pagination + Sort)
    public Page<Ride> advancedSearch(
            String text,
            String status,
            String sortField,
            String order,
            int page,
            int size
    ) {
        Criteria criteria = new Criteria().andOperator(
                statusCriteria(status),
                keywordCriteria(text)
        );

        Query query = new Query(criteria);

        Sort.Direction direction =
                order.equalsIgnoreCase("asc")
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC;

        query.with(Sort.by(direction, sortField));
        query.with(PageRequest.of(page, size));

        List<Ride> rides = mongoTemplate.find(query, Ride.class);

        return new PageImpl<>(rides);
    }
    // Rides by User
    public List<Ride> findRidesByUser(String userId) {
        Query query = new Query(
                Criteria.where("userId").is(userId)
        );
        return mongoTemplate.find(query, Ride.class);
    }
    //  Rides by User + Status
    public List<Ride> findRidesByUserAndStatus(String userId, String status) {

        Criteria criteria = new Criteria().andOperator(
                Criteria.where("userId").is(userId),
                Criteria.where("status").is(status)
        );

        Query query = new Query(criteria);
        return mongoTemplate.find(query, Ride.class);
    }
}