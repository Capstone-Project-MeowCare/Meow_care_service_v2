package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface BookingDetailRepository extends JpaRepository<BookingDetail, UUID> {

    @Query("SELECT bd FROM BookingDetail bd WHERE bd.booking.sitter.id = :sitterId AND " +
            "((bd.startTime < :endTime AND bd.endTime > :startTime) OR " +
            "(bd.startTime < :endTime AND bd.endTime IS NULL)) AND " +
            "bd.booking.status NOT IN (com.meow_care.meow_care_service.enums.BookingOrderStatus.CANCELLED, com.meow_care.meow_care_service.enums.BookingOrderStatus.NOT_CONFIRMED)")
    List<BookingDetail> findConflictingDetails(@Param("sitterId") UUID sitterId,
                                               @Param("startTime") Instant startTime,
                                               @Param("endTime") Instant endTime);

    @Query("select b from BookingDetail b where b.booking.id = ?1")
    List<BookingDetail> findByBooking_Id(UUID id);


}