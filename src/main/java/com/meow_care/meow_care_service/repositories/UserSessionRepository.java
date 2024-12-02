package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {
    @Transactional
    @Modifying
    @Query("delete from UserSession u where u.deviceId = ?1")
    void deleteByDeviceId(String deviceId);

}
