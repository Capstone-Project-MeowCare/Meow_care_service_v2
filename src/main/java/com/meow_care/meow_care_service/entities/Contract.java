package com.meow_care.meow_care_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "contracts")
public class Contract {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private BookingOrder booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sitter_id")
    private User sitter;

    @Size(max = 100)
    @Column(name = "owner_name", length = 100)
    private String ownerName;

    @Size(max = 255)
    @Column(name = "owner_address")
    private String ownerAddress;

    @Size(max = 15)
    @Column(name = "owner_phone", length = 15)
    private String ownerPhone;

    @Size(max = 100)
    @Column(name = "owner_email", length = 100)
    private String ownerEmail;

    @Size(max = 100)
    @Column(name = "sitter_name", length = 100)
    private String sitterName;

    @Size(max = 255)
    @Column(name = "sitter_address")
    private String sitterAddress;

    @Size(max = 15)
    @Column(name = "sitter_phone", length = 15)
    private String sitterPhone;

    @Size(max = 100)
    @Column(name = "sitter_email", length = 100)
    private String sitterEmail;

    @Column(name = "contract_terms", length = Integer.MAX_VALUE)
    private String contractTerms;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "deposit_amount")
    private BigDecimal depositAmount;

    @Column(name = "remaining_amount")
    private BigDecimal remainingAmount;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "contract_url")
    private String contractUrl;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

}