package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
}