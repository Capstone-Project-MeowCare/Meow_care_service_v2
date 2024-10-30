package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.PetItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PetItemRepository extends JpaRepository<PetItem, UUID> {
}