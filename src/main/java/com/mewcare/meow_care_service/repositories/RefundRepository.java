package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RefundRepository extends JpaRepository<Refund, UUID> {
}