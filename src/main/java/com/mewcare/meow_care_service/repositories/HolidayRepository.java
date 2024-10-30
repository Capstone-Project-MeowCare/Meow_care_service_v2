package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HolidayRepository extends JpaRepository<Holiday, UUID> {
}