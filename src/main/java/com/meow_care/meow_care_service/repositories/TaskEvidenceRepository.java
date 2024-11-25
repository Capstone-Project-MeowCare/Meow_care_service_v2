package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.TaskEvidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TaskEvidenceRepository extends JpaRepository<TaskEvidence, UUID> {
    @Query("select t from TaskEvidence t where t.task.id = ?1")
    List<TaskEvidence> findByTask_Id(UUID id);

}