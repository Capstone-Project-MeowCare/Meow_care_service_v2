package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}