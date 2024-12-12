package com.meow_care.meow_care_service.entities;

import com.meow_care.meow_care_service.enums.ServiceStatus;
import com.meow_care.meow_care_service.enums.ServiceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "services")
public class Service {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "action_description", length = Integer.MAX_VALUE)
    private String actionDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "sitter_profile_id")
    private SitterProfile sitterProfile;

    @ManyToMany(mappedBy = "services")
    @Builder.Default
    private Set<BookingSlotTemplate> bookingSlotTemplates = new LinkedHashSet<>();

    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @Column(name = "price", nullable = false)
    private Integer price;

    private LocalTime startTime;

    private LocalTime endTime;

    @Column(nullable = false)
    private ServiceStatus status;

    @Builder.Default
    private Boolean isDeleted = false;
}