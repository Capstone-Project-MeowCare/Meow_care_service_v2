package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.Service;
import com.meow_care.meow_care_service.enums.ServiceStatus;
import com.meow_care.meow_care_service.enums.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, UUID> {
    @Query("select s from Service s where s.sitterProfile.user.id = ?1 order by s.startTime")
    List<Service> findBySitterProfile_User_Id(UUID id);

    @Query("select s from Service s where s.serviceType = ?1 order by s.startTime")
    List<Service> findByServiceType(ServiceType serviceType);

    @Query("select s from Service s where s.serviceType = ?1 and s.status = ?2 order by s.startTime")
    List<Service> findByServiceTypeAndStatus(ServiceType serviceType, ServiceStatus status);

    @Query("select s from Service s where s.sitterProfile.user.id = ?1 and s.serviceType = ?2 order by s.startTime")
    List<Service> findBySitterProfile_User_IdAndServiceType(UUID id, ServiceType serviceType);

    @Query("select s from Service s where s.sitterProfile.user.id = ?1 and s.serviceType = ?2 and s.status = ?3 order by s.startTime")
    List<Service> findBySitterProfile_User_IdAndServiceTypeAndStatus(UUID id, ServiceType serviceType, ServiceStatus status);

    @Transactional
    @Modifying
    @Query("update Service s set s.status = ?1 where s.id = ?2")
    int updateStatusById(ServiceStatus status, UUID id);


    @Transactional
    @Modifying
    @Query("update Service s set s.name = ?1 where s.name = ?2")
    void updateNameByName(String newName, String oldName);
}