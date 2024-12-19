package com.meow_care.meow_care_service.repositories.specification;

import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.entities.SitterUnavailableDate;
import com.meow_care.meow_care_service.enums.ServiceStatus;
import com.meow_care.meow_care_service.enums.ServiceType;
import com.meow_care.meow_care_service.enums.SitterProfileStatus;
import com.meow_care.meow_care_service.enums.UnavailableDateType;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class SitterProfileSpecifications {

    public static Specification<SitterProfile> search(double latitude, double longitude, ServiceType serviceType, LocalDate startTime, LocalDate endTime) {
        return Specification.where(serviceType != null ? hasActiveServiceOfTypeWithGroupBy(serviceType) : null)
                .and(startTime != null && endTime != null ? hasNoUnavailableDates(startTime, endTime) : null)
                .and(orderByEuclideanDistance(latitude, longitude))
                .and(filterByActiveStatus());
    }

    public static Specification<SitterProfile> hasActiveServiceOfTypeWithGroupBy(ServiceType serviceType) {
        return (root, query, builder) -> {
            // Join services
            var servicesJoin = root.join("services");

            // Add serviceType and status predicates
            var serviceTypePredicate = builder.equal(servicesJoin.get("serviceType"), serviceType);
            var statusPredicate = builder.equal(servicesJoin.get("status"), ServiceStatus.ACTIVE);

            // Group by SitterProfile ID
            assert query != null;
            query.groupBy(root.get("id"));

            // Add HAVING COUNT(s.id) > 0 condition
            query.having(builder.gt(builder.count(servicesJoin.get("id")), 0));

            // Combine predicates
            return builder.and(serviceTypePredicate, statusPredicate);
        };
    }

    public static Specification<SitterProfile> hasNoUnavailableDates(LocalDate startTime, LocalDate endTime) {
        return (root, query, builder) -> {
            // Subquery for checking unavailable dates
            Subquery<SitterUnavailableDate> subquery = query.subquery(SitterUnavailableDate.class);
            Root<SitterUnavailableDate> unavailableDate = subquery.from(SitterUnavailableDate.class);

            // Build conditions for each type of unavailable date
            Predicate rangeOverlap = builder.and(
                    builder.equal(unavailableDate.get("type"), UnavailableDateType.RANGE),
                    builder.lessThanOrEqualTo(unavailableDate.get("startDate"), endTime),
                    builder.greaterThanOrEqualTo(unavailableDate.get("endDate"), startTime)
            );

            Predicate singleDateOverlap = builder.and(
                    builder.equal(unavailableDate.get("type"), UnavailableDateType.DATE),
                    builder.between(unavailableDate.get("date"), startTime, endTime)
            );

            // For day of week, check if any day in the range matches
            Predicate dayOfWeekOverlap = builder.and(
                    builder.equal(unavailableDate.get("type"), UnavailableDateType.DAY_OF_WEEK),
                    unavailableDate.get("dayOfWeek").in(
                            startTime.datesUntil(endTime.plusDays(1))
                                    .map(date -> date.getDayOfWeek().toString())
                                    .collect(Collectors.toList())
                    )
            );

            // Combine all conditions
            subquery.select(unavailableDate)
                    .where(
                            builder.and(
                                    builder.equal(unavailableDate.get("sitterProfile"), root),
                                    builder.or(
                                            rangeOverlap,
                                            singleDateOverlap,
                                            dayOfWeekOverlap
                                    )
                            )
                    );

            // Main query - return sitter profiles where no unavailable dates exist
            return builder.not(builder.exists(subquery));
        };
    }

    public static Specification<SitterProfile> orderByLocationSimilarity(String location) {
        return (root, query, builder) -> {
            // Use a similarity function to calculate the similarity score
            var similarityScore = builder.function("similarity", Double.class, root.get("location"), builder.literal(location));

            // Order by similarity score
            query.orderBy(builder.asc(similarityScore));

            return builder.conjunction();
        };
    }

    public static Specification<SitterProfile> orderByEuclideanDistance(Double latitude, Double longitude) {
        return (root, query, builder) -> {
            // Calculate Euclidean distance components
            Expression<Double> latDiff = builder.diff(root.get("latitude"), latitude);
            Expression<Double> lonDiff = builder.diff(root.get("longitude"), longitude);

            // Calculate squared differences
            Expression<Double> latDiffSquared = builder.prod(latDiff, latDiff);
            Expression<Double> lonDiffSquared = builder.prod(lonDiff, lonDiff);

            // Sum squared differences for Euclidean distance
            Expression<Double> distance = builder.sum(latDiffSquared, lonDiffSquared);

            // Order by calculated distance
            query.orderBy(builder.asc(distance));

            return builder.conjunction();
        };
    }

    public static Specification<SitterProfile> filterByActiveStatus() {
        return (root, query, builder) -> builder.equal(root.get("status"), SitterProfileStatus.ACTIVE);
    }
}
