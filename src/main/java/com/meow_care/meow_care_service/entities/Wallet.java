package com.meow_care.meow_care_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "wallets")
@EntityListeners(AuditingEntityListener.class)
public class Wallet {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "balance")
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "hold_balance")  // Updated field name
    @Builder.Default
    private BigDecimal holdBalance = BigDecimal.ZERO;

    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    // Method to add to holdBalance when a booking is confirmed
    public void addHoldBalance(BigDecimal amount) {
        this.holdBalance = this.holdBalance.add(amount);
    }

    @SuppressWarnings("unused")
    // Method to subtract from holdBalance when a booking is cancelled
    public void subtractHoldBalance(BigDecimal amount) {
        this.holdBalance = this.holdBalance.subtract(amount);
    }

    public void holdBalanceToBalance(BigDecimal amount) {
        this.holdBalance = this.holdBalance.subtract(amount);
        this.balance = this.balance.add(amount);
    }
}