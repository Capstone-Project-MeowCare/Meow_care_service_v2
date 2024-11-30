package com.meow_care.meow_care_service.entities;

import com.meow_care.meow_care_service.enums.WithdrawStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
@Table(name="request_withdrawal")
@EntityListeners(AuditingEntityListener.class)
public class RequestWithdrawal {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name="balance",nullable=false)
    private BigDecimal balance;

    @NotNull
    @Column(name="created_at",nullable=false)
    @CreatedDate
    private Instant createAt;

    @Column(name="updated_at")
    @LastModifiedDate
    private Instant updatedAt;

    @NotNull
    @Column(name="process_status",nullable=false)
    private WithdrawStatus processStatus;

    @Column
    private boolean deleted;

    @Column
    private String bankNumber;

    @Column
    private String fullName;

    @Column
    private String bankName;

    @NotNull
    @ManyToOne
    @JoinColumn(name="wallet_id",nullable=false)
    private Wallet wallet;

}
