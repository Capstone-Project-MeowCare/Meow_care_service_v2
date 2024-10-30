package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
}