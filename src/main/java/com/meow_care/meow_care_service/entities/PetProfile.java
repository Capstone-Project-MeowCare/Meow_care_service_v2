package com.meow_care.meow_care_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "pet_profiles")
public class PetProfile {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "pet_profiles_medical_conditions",
            joinColumns = @JoinColumn(name = "pet_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "medical_condition_id"))
    private Set<MedicalCondition> medicalConditions = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "petProfiles")
    private Set<Task> tasks = new LinkedHashSet<>();

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Size(max = 50)
    @NotNull
    @Column(name = "pet_name", nullable = false, length = 50)
    private String petName;

    @Size(max = 50)
    @Column(name = "breed", length = 50)
    private String breed;

    @Column(name = "age")
    private Integer age;

    @Size(max = 10)
    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "weight")
    private BigDecimal weight;

    @Size(max = 255)
    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;



}