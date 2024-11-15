package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, UUID> {
    @Query("select s from Service s where s.configService.serviceType.id = ?1")
    List<Service> findByConfigServiceServiceTypeId(UUID id);

    @Query("select s from Service s where s.sitter.id = ?1 and s.status = 0")
    List<Service> findBySitterId(UUID id);


}