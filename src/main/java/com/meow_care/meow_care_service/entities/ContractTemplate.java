package com.meow_care.meow_care_service.entities;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "contract_templates")
@EntityListeners(AuditingEntityListener.class)
public class ContractTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name; // Name of the template

    @Lob
    @Column(nullable = false)
    private String content; // The HTML content of the template

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "contract_template_fields", joinColumns = @JoinColumn(name = "template_id"))
    @Column
    @Builder.Default
    private List<String> contractFields = new ArrayList<>(); // List of fields in the template

    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt; // Timestamp when the template was created

    @LastModifiedDate
    @Column
    private Instant updatedAt;
}
