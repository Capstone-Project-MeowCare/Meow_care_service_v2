package com.meow_care.meow_care_service.entities;

import com.meow_care.meow_care_service.enums.TaskStatus;
import com.meow_care.meow_care_service.enums.TaskType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Table(name = "tasks")
@EntityListeners(AuditingEntityListener.class)
public class Task {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private CareSchedule session;

    @ManyToOne
    @JoinColumn(name = "pet_profile_id")
    private PetProfile petProfile;

    @Builder.Default
    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<TaskEvidence> taskEvidences = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "task_parent_id")
    private Task taskParent;

    @OneToMany(mappedBy = "taskParent", orphanRemoval = true)
    private Set<Task> tasks = new LinkedHashSet<>();

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    private String comment;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private TaskType taskType;

    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    @Column
    private Instant updatedAt;

}