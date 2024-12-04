package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.BookingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.UUID;

public interface BookingSlotRepository extends JpaRepository<BookingSlot, UUID> {

    @Query("SELECT CASE WHEN COUNT(bs) > 0 " +
            "THEN TRUE ELSE FALSE END " +
            "FROM BookingSlot bs " +
            "WHERE bs.startTime <= :time AND bs.endTime >= :time")
    boolean existsByTimeBetweenStartTimeAndEndTime(Instant time);

}