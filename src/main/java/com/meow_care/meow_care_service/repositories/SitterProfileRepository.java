package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.enums.SitterProfileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SitterProfileRepository extends JpaRepository<SitterProfile, UUID> {
    Optional<SitterProfile> findByUserId(UUID id);

    @Query("select (count(s) > 0) from SitterProfile s where s.user.id = ?1")
    boolean existsByUserId(UUID id);

    @Query("select s from SitterProfile s where s.status = ?1")
    List<SitterProfile> findByStatus(SitterProfileStatus status);

    @Transactional
    @Modifying
    @Query("update SitterProfile s set s.status = ?1 where s.id = ?2")
    int updateStatusById(SitterProfileStatus status, UUID id);

}