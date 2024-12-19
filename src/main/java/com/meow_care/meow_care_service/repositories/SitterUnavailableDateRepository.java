package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.SitterUnavailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SitterUnavailableDateRepository extends JpaRepository<SitterUnavailableDate, UUID> {
    @Query("select s from SitterUnavailableDate s where s.sitterProfile.user.id = ?1")
    List<SitterUnavailableDate> findBySitterProfile_User_Id(UUID id);

}