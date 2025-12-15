package com.aditeey.uber.service;

import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class AnalyticsService {

    private final MongoTemplate mongoTemplate;

    public AnalyticsService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    // 17.1 — Rides per day
    public List<Document> ridesPerDay() {
        Aggregation aggregation = newAggregation(
                group("createdAt").count().as("totalRides"),
                sort(Sort.Direction.ASC, "_id")
        );

        return mongoTemplate
                .aggregate(aggregation, "rides", Document.class)
                .getMappedResults();
    }

    // 17.2 — Driver summary
    public Document driverSummary(String driverId) {

        Aggregation aggregation = newAggregation(
                match(Criteria.where("driverId").is(driverId)),
                group("driverId")
                        .count().as("totalRides")
                        .sum("fareAmount").as("totalFare")
        );

        return mongoTemplate
                .aggregate(aggregation, "rides", Document.class)
                .getUniqueMappedResult();
    }

    // 17.3 — Status summary
    public List<Document> statusSummary() {

        Aggregation aggregation = newAggregation(
                group("status").count().as("count")
        );

        return mongoTemplate
                .aggregate(aggregation, "rides", Document.class)
                .getMappedResults();
    }
}