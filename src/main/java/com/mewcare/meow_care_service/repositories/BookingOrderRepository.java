package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.BookingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface BookingOrderRepository extends JpaRepository<BookingOrder, UUID> {

    @Query("select b from BookingOrder b where b.user.id = ?1")
    Optional<BookingOrder> findByUserId(UUID id);

    @Query("select b from BookingOrder b where b.sitter.id = ?1")
    Optional<BookingOrder> findBySitterId(UUID id);


}