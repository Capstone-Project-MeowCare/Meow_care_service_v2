package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.BookingOrder;
import com.meow_care.meow_care_service.enums.BookingOrderStatus;
import com.meow_care.meow_care_service.enums.OrderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingOrderRepository extends JpaRepository<BookingOrder, UUID> {

    @Query("select b from BookingOrder b where b.user.id = ?1")
    List<BookingOrder> findByUserId(UUID id);

    @Query("select b from BookingOrder b where b.sitter.id = ?1")
    List<BookingOrder> findBySitterId(UUID id);

    @Query("select b from BookingOrder b where b.user.id = ?1 and b.status != ?2")
    Page<BookingOrder> findByUser_Id(UUID id, BookingOrderStatus status, Pageable pageable);

    @Query("select b from BookingOrder b where b.sitter.id = ?1 and b.status != ?2")
    Page<BookingOrder> findBySitter_Id(UUID id,BookingOrderStatus status, Pageable pageable);

    @Query("select b from BookingOrder b where b.user.id = ?1 and b.status = ?2")
    Page<BookingOrder> findByUser_IdAndStatus(UUID id, BookingOrderStatus status, Pageable pageable);

    @Query("""
                select b from BookingOrder b
                where b.user.id = ?1
                  and (?2 is null or b.status = ?2)
            """)
    Page<BookingOrder> findByUser_IdAndOptionalStatus(UUID id, @Nullable BookingOrderStatus status, Pageable pageable);

    @Query("select b from BookingOrder b where b.sitter.id = ?1 and b.status = ?2")
    Page<BookingOrder> findBySitter_IdAndStatus(UUID id, BookingOrderStatus status, Pageable pageable);

    @Query("""
                select b from BookingOrder b
                where b.sitter.id = ?1
                  and (?2 is null or b.status = ?2)
                  and (b.status is null or b.status <> 'AWAITING_PAYMENT')
            """)
    Page<BookingOrder> findBySitter_IdAndOptionalStatus(UUID id, @Nullable BookingOrderStatus status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update BookingOrder b set b.status = ?1 where b.id = ?2")
    int updateStatusById(BookingOrderStatus status, UUID id);

    @Query("select count(b) from BookingOrder b where b.createdAt between ?1 and ?2")
    long countByCreatedAtBetween(Instant createdAtStart, Instant createdAtEnd);

    @Query("select count(b) from BookingOrder b where ?1 is null or b.status = ?1")
    long countByStatus(@Nullable BookingOrderStatus status);

    @Query("select count(b) from BookingOrder b where b.sitter.id = ?1 and (?2 is null or b.status = ?2) and (?3 is null or b.orderType = ?3)")
    long countBySitter_IdAndStatusAndOrderType(UUID id, @Nullable BookingOrderStatus status, @Nullable OrderType orderType);


    Optional<BookingOrder> findFirstByTransactionsId(UUID transactionId);

    long countByUser_IdAndStatusAndOrderType(UUID userId, BookingOrderStatus status, OrderType orderType);

    @Query("select b from BookingOrder b where b.status = ?1 and b.startDate < ?2")
    List<BookingOrder> findByStatusAndStartDateBefore(BookingOrderStatus status, Instant startDate);
}