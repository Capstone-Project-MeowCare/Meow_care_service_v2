package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.BookingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingOrderRepository extends JpaRepository<BookingOrder, UUID> {
}