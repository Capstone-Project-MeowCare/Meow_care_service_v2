package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.PetProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PetProfileRepository extends JpaRepository<PetProfile, UUID> {
}