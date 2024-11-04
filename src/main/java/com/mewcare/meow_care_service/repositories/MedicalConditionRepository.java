package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.MedicalCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicalConditionRepository extends JpaRepository<MedicalCondition, UUID> {
}