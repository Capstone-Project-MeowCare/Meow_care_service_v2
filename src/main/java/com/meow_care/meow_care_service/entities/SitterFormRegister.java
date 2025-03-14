package com.meow_care.meow_care_service.entities;

import com.meow_care.meow_care_service.enums.SitterFormRegisterStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SitterFormRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "sitterFormRegister", orphanRemoval = true, cascade = CascadeType.PERSIST)
    @Builder.Default
    private Set<Certificate> certificates = new LinkedHashSet<>();

    private String fullName;

    private String email;

    private String phoneNumber;

    private String address;

    private SitterFormRegisterStatus status;


}
