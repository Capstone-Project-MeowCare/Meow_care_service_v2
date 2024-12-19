package com.meow_care.meow_care_service.entities;

import com.meow_care.meow_care_service.enums.SitterProfileStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sitter_profile")
@EntityListeners(AuditingEntityListener.class)
public class SitterProfile {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne()
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "sitterProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<ProfilePicture> profilePictures = new LinkedHashSet<>();

    @OneToMany(mappedBy = "sitterProfile", orphanRemoval = true)
    @Builder.Default
    private Set<Certificate> certificates = new LinkedHashSet<>();

    @OneToMany(mappedBy = "sitterProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Service> services = new LinkedHashSet<>();

    @OneToMany(mappedBy = "sitterProfile", orphanRemoval = true)
    @Builder.Default
    private Set<SitterUnavailableDate> sitterUnavailableDates = new LinkedHashSet<>();

    @Column(name = "bio", length = Integer.MAX_VALUE)
    private String bio;

    @Column(name = "experience", length = Integer.MAX_VALUE)
    private String experience;

    @Column(name = "skill", length = Integer.MAX_VALUE)
    private String skill;

    @Column(name = "rating")
    private BigDecimal rating;

    @Size(max = 255)
    @Column(name = "location")
    private String location;

    private Double latitude;

    private Double longitude;

    @Size(max = 255)
    @Column(name = "environment")
    private String environment;

    @Column(name = "maximum_quantity")
    private Integer maximumQuantity;

    @Column(nullable = false)
    private Integer fullRefundDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SitterProfileStatus status;

    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Transient
    private Double distance;

}