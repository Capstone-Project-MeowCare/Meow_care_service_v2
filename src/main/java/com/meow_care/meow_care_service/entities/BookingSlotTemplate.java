package com.meow_care.meow_care_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Table(name = "booking_slot_templates")
public class BookingSlotTemplate {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sitter_profile_id")
    private SitterProfile sitterProfile;

    @ManyToMany
    @JoinTable(name = "booking_slot_templates_services",
            joinColumns = @JoinColumn(name = "booking_slot_templates_id"),
            inverseJoinColumns = @JoinColumn(name = "services_id"))
    private Set<Service> services = new LinkedHashSet<>();

    @Column(nullable = false)
    private String name;

    private Integer duration;

    @Column(nullable = false)
    private Instant startTime;

    @Column(nullable = false)
    private Instant endTime;

}
