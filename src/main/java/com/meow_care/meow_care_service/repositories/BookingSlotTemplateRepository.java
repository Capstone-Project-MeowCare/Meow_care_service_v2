package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.BookingSlotTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BookingSlotTemplateRepository extends JpaRepository<BookingSlotTemplate, UUID> {

    @Query("select b from BookingSlotTemplate b where b.sitterProfile.user.id = ?1")
    List<BookingSlotTemplate> findBySitterProfile_User_Id(UUID id);

    @Query("select b from BookingSlotTemplate b inner join b.services services where services.id = ?1")
    List<BookingSlotTemplate> findByServices_Id(UUID id);


}