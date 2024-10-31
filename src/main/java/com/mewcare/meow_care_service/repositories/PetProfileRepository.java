package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.PetProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PetProfileRepository extends JpaRepository<PetProfile, UUID> {
    @Query("select p from PetProfile p where p.user.id = ?1")
    Optional<PetProfile> findByUserId(UUID id);
}