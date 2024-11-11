package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.CareSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CareScheduleRepository extends JpaRepository<CareSchedule, UUID> {

    Optional<CareSchedule> findByBookingId(UUID bookingId);

    @Query("select c from CareSchedule c where c.booking.sitter.id = ?1")
    List<CareSchedule> findByBookingSitterId(UUID id);


}