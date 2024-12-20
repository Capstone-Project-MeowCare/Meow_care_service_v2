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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class SitterProfileSpecifications {

    public static Specification<SitterProfile> search(Double latitude, Double longitude, ServiceType serviceType, LocalDate startTime, LocalDate endTime, BigDecimal minPrice, BigDecimal maxPrice, Integer minQuantity) {
        Specification<SitterProfile> spec = Specification.where(null);

        if (serviceType != null) {
            spec = spec.and(hasActiveServiceOfTypeWithGroupBy(serviceType));
        }
        if (startTime != null && endTime != null) {
            spec = spec.and(hasNoUnavailableDates(startTime, endTime));
        }
        if (latitude != null && longitude != null) {
            spec = spec.and(orderByEuclideanDistance(latitude, longitude));
        }
        if (minPrice != null || maxPrice != null) {
            spec = spec.and(filterByMainServicePrice(minPrice, maxPrice));
        }
        if (minQuantity != null) {
            spec = spec.and(filterByMaximumQuantityGreaterThan(minQuantity));
        }
        spec = spec.and(filterByActiveStatus());

        return spec;
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
            assert query != null;
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
            assert query != null;
            query.orderBy(builder.asc(distance));

            return builder.conjunction();
        };
    }

    public static Specification<SitterProfile> filterByActiveStatus() {
        return (root, query, builder) -> builder.equal(root.get("status"), SitterProfileStatus.ACTIVE);
    }

    public static Specification<SitterProfile> filterByMainServicePrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, builder) -> {
            // Join services
            var servicesJoin = root.join("services");

            // Filter for main service type
            var mainServicePredicate = builder.equal(servicesJoin.get("serviceType"), ServiceType.MAIN_SERVICE);
            var statusPredicate = builder.equal(servicesJoin.get("status"), ServiceStatus.ACTIVE);


            // Filter by price range
            var pricePredicate = builder.between(servicesJoin.get("price"), minPrice, maxPrice);

            // Combine predicates
            return builder.and(mainServicePredicate, pricePredicate, statusPredicate);
        };
    }

    public static Specification<SitterProfile> orderByRating() {
        return (root, query, builder) -> {
            // Order by rating
            assert query != null;
            query.orderBy(builder.desc(root.get("rating")));

            return builder.conjunction();
        };
    }

    public static Specification<SitterProfile> filterByMaximumQuantityGreaterThan(Integer minQuantity) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("maximumQuantity"), minQuantity);
    }
}
