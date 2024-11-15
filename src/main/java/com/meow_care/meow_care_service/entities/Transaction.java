package com.meow_care.meow_care_service.entities;

import com.meow_care.meow_care_service.enums.PaymentMethod;
import com.meow_care.meow_care_service.enums.TransactionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
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
@Table(name = "transactions")
@EntityListeners(AuditingEntityListener.class)
public class Transaction {
    @Id
    @Column(name = "id", nullable = false)
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private User toUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private BookingOrder booking;

    @Column(name = "amount")
    private BigDecimal amount;

    @Builder.Default
    @Size(max = 10)
    @Column(name = "currency", length = 10)
    private String currency = "VND";

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", length = 50)
    private PaymentMethod paymentMethod;

    @Size(max = 50)
    @Column(name = "transaction_type", length = 50)
    private String transactionType;

    @Column(name = "wallet_amount")
    private BigDecimal walletAmount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    public void setToUserId(UUID toUserId) {
        this.toUser = User.builder().id(toUserId).build();
    }

    public void setFromUserId(UUID fromUserId) {
        this.fromUser = User.builder().id(fromUserId).build();
    }

    public void setBookingId(UUID bookingId) {
        this.booking = BookingOrder.builder().id(bookingId).build();
    }
}