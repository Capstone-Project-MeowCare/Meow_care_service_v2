package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReactionRepository extends JpaRepository<Reaction, UUID> {
}