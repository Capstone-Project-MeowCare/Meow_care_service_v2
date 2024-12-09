package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.BookingSlot;
import com.meow_care.meow_care_service.enums.BookingSlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookingSlotRepository extends JpaRepository<BookingSlot, UUID> {

    @Query("SELECT CASE WHEN COUNT(bs) > 0 " +
            "THEN TRUE ELSE FALSE END " +
            "FROM BookingSlot bs " +
            "WHERE bs.startTime <= :time AND bs.endTime >= :time")
    boolean existsByTimeBetweenStartTimeAndEndTime(Instant time);

    @Query("select b from BookingSlot b where b.bookingSlotTemplate.id = ?1 and b.startTime between ?2 and ?3")
    List<BookingSlot> findByBookingSlotTemplate_IdAndStartTimeBetween(UUID id, Instant startDate, Instant endDate);

    @Query("SELECT bs FROM BookingSlot bs WHERE bs.bookingSlotTemplate.sitterProfile.user.id = ?1 " +
            "AND CAST(bs.startTime AS date) = ?2 AND bs.status = ?3 ORDER BY bs.startTime")
    List<BookingSlot> findBySitterIdDateAndStatus(UUID sitterId, LocalDate date, BookingSlotStatus status);
}