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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Delegate;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(max = 100)
    @Column(name = "email", nullable = false, unique = true, length = 100, updatable = false)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false, length = Integer.MAX_VALUE)
    private String password;

    @Size(max = 255)
    @Column(name = "full_name")
    private String fullName;

    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;

    @Size(max = 15)
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "dob")
    private Instant dob;

    @Size(max = 20)
    @Column(name = "gender", length = 20)
    private String gender;

    @Size(max = 255)
    @Column(name = "address")
    private String address;

    @CreatedDate
    @Column(name = "registration_date", updatable = false)
    private Instant registrationDate;

    @Column(name = "status")
    private Integer status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    @Delegate
    @Builder.Default
    private Set<Role> roles = new LinkedHashSet<>();

}