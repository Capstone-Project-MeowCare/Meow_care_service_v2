package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, UUID> {
}