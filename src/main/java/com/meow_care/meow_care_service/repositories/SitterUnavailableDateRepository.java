package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.SitterUnavailableDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SitterUnavailableDateRepository extends JpaRepository<SitterUnavailableDate, UUID> {
    List<SitterUnavailableDate> findBySitterId(UUID id);
}