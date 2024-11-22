package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.UserQuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface UserQuizResultRepository extends JpaRepository<UserQuizResult, UUID> {
    List<UserQuizResult> findByUserId(UUID userId);

    @Query("select u from UserQuizResult u where u.user.id = ?1 and u.createdAt > ?2 and u.createdAt < ?3")
    List<UserQuizResult> findByUserIdAndCreatedAtGreaterThanAndCreatedAtLessThan(UUID userId, Instant firstDayOfMonth, Instant lastDayOfMonth);
}