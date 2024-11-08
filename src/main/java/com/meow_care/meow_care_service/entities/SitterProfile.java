package com.meow_care.meow_care_service.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "sitter_profile")
public class SitterProfile {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "sitterProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProfilePicture> profilePictures = new LinkedHashSet<>();

    @Column(name = "bio", length = Integer.MAX_VALUE)
    private String bio;

    @Column(name = "experience", length = Integer.MAX_VALUE)
    private String experience;

    @Size(max = 150)
    @Column(name = "skill", length = 150)
    private String skill;

    @Column(name = "rating")
    private BigDecimal rating;

    @Size(max = 255)
    @Column(name = "location")
    private String location;

    @Size(max = 255)
    @Column(name = "environment")
    private String environment;

    @Column(name = "maximum_quantity")
    private Integer maximumQuantity;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;


}