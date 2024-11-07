package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.TaskFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskFeedbackRepository extends JpaRepository<TaskFeedback, UUID> {
}