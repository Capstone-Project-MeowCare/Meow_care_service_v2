package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}