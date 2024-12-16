package com.meow_care.meow_care_service.repositories.specification;

import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.enums.SitterProfileStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class SitterProfileSpecifications {

    private static final double EARTH_RADIUS_KM = 6371;
    private static final double MAX_DISTANCE_KM = 1000;
    private static final double MIN_RATING = 0.0;

    public static Specification<SitterProfile> filterByName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filtering
            }
            return criteriaBuilder.like(root.get("user").get("fullName"), "%" + name + "%");
        };
    }

    public static Specification<SitterProfile> filterByStatus(String status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<SitterProfile> filterByStatus(SitterProfileStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<SitterProfile> filterByMinRating(Double minRating) {
        return (root, query, criteriaBuilder) -> {
            Double effectiveMinRating = (minRating != null) ? minRating : MIN_RATING;
            return criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), effectiveMinRating);
        };
    }

    public static Specification<SitterProfile> withinDistance(double latitude, double longitude) {
        return (root, query, criteriaBuilder) -> {
            Expression<Double> distance = calculateDistance(root, criteriaBuilder, latitude, longitude);
            return criteriaBuilder.lessThanOrEqualTo(distance, MAX_DISTANCE_KM);
        };
    }

    public static Specification<SitterProfile> orderByDistance(double latitude, double longitude) {
        return (root, query, criteriaBuilder) -> {
            if (query != null) {
                Expression<Double> distance = calculateDistance(root, criteriaBuilder, latitude, longitude);
                query.multiselect(root,
                        distance.alias("distance"));
                query.orderBy(criteriaBuilder.asc(distance));
            }
            return null;
        };
    }

    public static Specification<SitterProfile> findNearbySitters(String name, SitterProfileStatus status, Double minRating, double latitude, double longitude) {
        return Specification
                .where(filterByName(name))
                .and(filterByStatus(status))
                .and(filterByMinRating(minRating))
                .and(withinDistance(latitude, longitude))
                .and(orderByDistance(latitude, longitude));
    }

    private static Expression<Double> calculateDistance(Root<SitterProfile> root, CriteriaBuilder criteriaBuilder, double latitude, double longitude) {
        // Convert to radians
        Expression<Double> latRadians = criteriaBuilder.function("radians", Double.class, root.get("latitude"));
        Expression<Double> lonRadians = criteriaBuilder.function("radians", Double.class, root.get("longitude"));
        Expression<Double> userLatRadians = criteriaBuilder.function("radians", Double.class,
                criteriaBuilder.literal(latitude));
        Expression<Double> userLonRadians = criteriaBuilder.function("radians", Double.class,
                criteriaBuilder.literal(longitude));

        // Differences
        Expression<Double> latDiff = criteriaBuilder.diff(latRadians, userLatRadians);
        Expression<Double> lonDiff = criteriaBuilder.diff(lonRadians, userLonRadians);

        // Haversine formula
        Expression<Double> a = criteriaBuilder.sum(
                criteriaBuilder.prod(
                        criteriaBuilder.function("sin", Double.class, criteriaBuilder.quot(latDiff, 2)),
                        criteriaBuilder.function("sin", Double.class, criteriaBuilder.quot(latDiff, 2))),
                criteriaBuilder.prod(
                        criteriaBuilder.prod(
                                criteriaBuilder.function("cos", Double.class, latRadians),
                                criteriaBuilder.function("cos", Double.class, userLatRadians)),
                        criteriaBuilder.prod(
                                criteriaBuilder.function("sin", Double.class, criteriaBuilder.quot(lonDiff, 2)),
                                criteriaBuilder.function("sin", Double.class, criteriaBuilder.quot(lonDiff, 2)))));

        Expression<Double> c = criteriaBuilder.prod(
                criteriaBuilder.literal(2D),
                criteriaBuilder.function("atan2", Double.class,
                        criteriaBuilder.sqrt(a),
                        criteriaBuilder.sqrt(criteriaBuilder.diff(1D, a))));

        return criteriaBuilder.prod(criteriaBuilder.literal(EARTH_RADIUS_KM), c);
    }

}
