package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BookingDetailRepository extends JpaRepository<BookingDetail, UUID> {

    @Query("select b from BookingDetail b where b.booking.id = ?1")
    List<BookingDetail> findAllByBookingId(UUID bookingId);
}