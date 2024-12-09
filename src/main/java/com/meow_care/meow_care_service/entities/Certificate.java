package com.meow_care.meow_care_service.entities;

import com.meow_care.meow_care_service.enums.CertificateType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "certificates")
@EntityListeners(AuditingEntityListener.class)
public class Certificate {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sitter_profile_id")
    private SitterProfile sitterProfile;

    @Size(max = 100)
    @Column(name = "certificate_name", length = 100)
    private String certificateName;

    @Size(max = 100)
    @Column(name = "institution_name", length = 100)
    private String institutionName;

    @Column(name = "issue_date")
    private Instant issueDate;

    @Column(name = "expiry_date")
    private Instant expiryDate;

    @Size(max = 255)
    @Column(name = "certificate_url")
    private String certificateUrl;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    private CertificateType certificateType;

    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

}