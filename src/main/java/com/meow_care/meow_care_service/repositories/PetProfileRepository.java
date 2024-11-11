package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.PetProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PetProfileRepository extends JpaRepository<PetProfile, UUID> {
    List<PetProfile> findByUserId(UUID id);

    @Query("select p from PetProfile p inner join p.tasks tasks where tasks.id = ?1")
    List<PetProfile> findByTasksId(UUID id);


}