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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sitter_id")
    private User sitter;

    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @Column(name = "price")
    private Integer price;

    private Integer duration;

    @Column(columnDefinition = "INTEGER CHECK (start_time >= 0 AND start_time <= 24)")
    private Integer startTime;

    @Column(columnDefinition = "INTEGER CHECK (end_time >= 0 AND end_time <= 24)")
    private Integer endTime;

    private ServiceStatus status;
}