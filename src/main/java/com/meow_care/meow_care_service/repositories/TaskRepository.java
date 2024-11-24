package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.Task;
import com.meow_care.meow_care_service.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Query("select t from Task t where t.status = ?1")
    List<Task> findByStatus(TaskStatus status);

    //get by status with task evidence
    @Query("select t from Task t left join fetch t.taskEvidences where t.status = ?1")
    List<Task> findByStatusWithTaskEvidence(TaskStatus status);

}