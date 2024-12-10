package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, UUID> {
}