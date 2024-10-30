package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.ConfigService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConfigServiceRepository extends JpaRepository<ConfigService, UUID> {
}