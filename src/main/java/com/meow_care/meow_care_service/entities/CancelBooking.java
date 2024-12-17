package com.meow_care.meow_care_service.entities;

import com.meow_care.meow_care_service.enums.CancelBookingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cancel_bookings")
public class CancelBooking {
    @Id
    @GeneratedValue
    private UUID id;

    @Column()
    private String reason;

    @Enumerated(EnumType.STRING)
    private CancelBookingStatus status;

    private Instant requestedAt;

    private Instant ownerApprovalAt;

    private Instant sitterApprovalAt;

    @ManyToOne
    @JoinColumn(name = "booking_order_id")
    private BookingOrder bookingOrder;
}
