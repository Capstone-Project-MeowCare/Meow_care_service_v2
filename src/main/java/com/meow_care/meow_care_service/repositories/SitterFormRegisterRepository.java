package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.SitterFormRegister;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SitterFormRegisterRepository extends JpaRepository<SitterFormRegister, UUID> {
    Optional<SitterFormRegister> findByUserId(UUID userId);
}