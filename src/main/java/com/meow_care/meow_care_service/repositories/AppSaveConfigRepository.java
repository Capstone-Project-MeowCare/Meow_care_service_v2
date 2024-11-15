package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.AppSaveConfig;
import com.meow_care.meow_care_service.enums.ConfigKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AppSaveConfigRepository extends JpaRepository<AppSaveConfig, UUID> {
    @Query("select a from AppSaveConfig a where a.configKey = ?1")
    Optional<AppSaveConfig> findByConfigKey(ConfigKey configKey);
}