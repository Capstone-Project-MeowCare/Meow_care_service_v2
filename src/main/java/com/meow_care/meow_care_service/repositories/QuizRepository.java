package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuizRepository extends JpaRepository<Quiz, UUID> {
}