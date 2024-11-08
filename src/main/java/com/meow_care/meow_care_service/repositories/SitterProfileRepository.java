package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.SitterProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SitterProfileRepository extends JpaRepository<SitterProfile, UUID> {
    Optional<SitterProfile> findByUserId(UUID id);
}