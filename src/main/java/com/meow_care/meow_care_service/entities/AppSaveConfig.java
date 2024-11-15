package com.meow_care.meow_care_service.entities;

import com.meow_care.meow_care_service.enums.ConfigKey;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "app_save_configs")
@EntityListeners(AuditingEntityListener.class)
public class AppSaveConfig {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private ConfigKey configKey;

    private String configValue;

    private String description;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private Instant updatedAt;

    private boolean isActive;

}
