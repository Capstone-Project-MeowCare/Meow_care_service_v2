package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.BookingSlot;
import com.meow_care.meow_care_service.enums.BookingSlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookingSlotRepository extends JpaRepository<BookingSlot, UUID> {

    @Query("SELECT CASE WHEN COUNT(bs) > 0 " +
            "THEN TRUE ELSE FALSE END " +
            "FROM BookingSlot bs " +
            "WHERE bs.bookingSlotTemplate.sitterProfile.id = :sitterId " +
            "AND bs.startTime < :endTime " +
            "AND bs.endTime > :startTime")
    boolean existsOverlappingSlots(Instant startTime, Instant endTime, UUID sitterId);

    @Query("select b from BookingSlot b where b.bookingSlotTemplate.id = ?1 and b.startTime between ?2 and ?3")
    List<BookingSlot> findByBookingSlotTemplate_IdAndStartTimeBetween(UUID id, Instant startDate, Instant endDate);

    @Query("SELECT bs FROM BookingSlot bs WHERE bs.bookingSlotTemplate.sitterProfile.user.id = ?1 " +
            "AND CAST(bs.startTime AS date) = ?2 AND bs.status = ?3 ORDER BY bs.startTime")
    List<BookingSlot> findBySitterIdDateAndStatus(UUID sitterId, LocalDate date, BookingSlotStatus status);

    @Query("SELECT bs FROM BookingSlot bs " +
            "JOIN bs.bookingSlotTemplate bst " +
            "JOIN bst.services s " +
            "WHERE bst.sitterProfile.user.id = ?1 " +
            "AND s.id = ?2 " +
            "AND CAST(bs.startTime AS date) = ?3 " +
            "AND bs.status = ?4 " +
            "ORDER BY bs.startTime")
    List<BookingSlot> findBySitterIdServiceIdDateAndStatus(
            UUID sitterId,
            UUID serviceId,
            LocalDate date,
            BookingSlotStatus status);

    @Transactional
    @Modifying
    @Query("update BookingSlot b set b.status = ?1 where b.id = ?2")
    int updateStatusById(BookingSlotStatus status, UUID id);

    @Query("select b from BookingSlot b where b.bookingSlotTemplate.id = ?1 and b.status = ?2")
    List<BookingSlot> findByBookingSlotTemplate_IdAndStatus(UUID id, BookingSlotStatus status);


}