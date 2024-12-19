package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.enums.SitterProfileStatus;
import com.meow_care.meow_care_service.projection.SitterProfileInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SitterProfileRepository extends JpaRepository<SitterProfile, UUID>, JpaSpecificationExecutor<SitterProfile> {
    Optional<SitterProfile> findByUserId(UUID id);

    @Query("select (count(s) > 0) from SitterProfile s where s.user.id = ?1")
    boolean existsByUserId(UUID id);

    @Query("select s from SitterProfile s where s.status = ?1")
    List<SitterProfile> findByStatus(SitterProfileStatus status);

    @Transactional
    @Modifying
    @Query("update SitterProfile s set s.status = ?1 where s.id = ?2")
    int updateStatusById(SitterProfileStatus status, UUID id);

    @Query(value = """
                SELECT s.id AS id, s.bio AS bio, s.experience AS experience, s.skill AS skill, s.rating AS rating,
                       s.location AS location, s.latitude AS latitude, s.longitude AS longitude, s.environment AS environment,
                       s.maximum_quantity AS maximumQuantity, s.status AS status, s.created_at AS createdAt, s.updated_at AS updatedAt,
                       u.id AS userId, u.full_name AS fullName, u.avatar AS avatar,
                       (6371 * acos(
                           cos(radians(:latitude)) * cos(radians(s.latitude)) *
                           cos(radians(s.longitude) - radians(:longitude)) +
                           sin(radians(:latitude)) * sin(radians(s.latitude))
                       )) AS distance
                FROM sitter_profile s
                JOIN users u ON s.user_id = u.id
                WHERE (u.full_name LIKE CONCAT('%', :name, '%') OR :name IS NULL) AND s.status = 'ACTIVE'
            """, nativeQuery = true)
    Page<SitterProfileInfo> findAllWithDistanceAndName(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("name") String name,
            Pageable pageable);

    @Query("select s from SitterProfile s where s.id in ?1")
    List<SitterProfile> findByIdIn(Collection<UUID> ids);

}