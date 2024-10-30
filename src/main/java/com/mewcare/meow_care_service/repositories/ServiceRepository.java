package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, UUID> {
}