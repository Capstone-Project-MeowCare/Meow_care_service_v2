package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    @Query("select r from Review r where r.user.id = ?1")
    List<Review> findByUserId(UUID userId);

    @Query("select r from Review r where r.bookingOrder.id = ?1")
    List<Review> findByBookingOrderId(UUID bookingOrderId);

    @Query("select r from Review r where r.bookingOrder.sitter.id = ?1")
    List<Review> findByBookingOrder_Sitter_Id(UUID id);

    @Query("select count(r) from Review r where r.bookingOrder.sitter.id = ?1")
    long countByBookingOrder_Sitter_Id(UUID id);


}