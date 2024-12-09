package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CertificateRepository extends JpaRepository<Certificate, UUID> {
    @Query("select c from Certificate c where c.sitterProfile.id = ?1")
    List<Certificate> findBySitterProfileId(UUID id);
}