package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.BookingOrder;
import com.meow_care.meow_care_service.enums.BookingOrderStatus;
import com.meow_care.meow_care_service.enums.OrderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collection;
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

    @Query("select count(b) from BookingOrder b where b.status = ?1 and b.updatedAt between ?2 and ?3")
    long countByStatusAndUpdatedAtBetween(BookingOrderStatus status, Instant updatedAtStart, Instant updatedAtEnd);


    Optional<BookingOrder> findFirstByTransactionsId(UUID transactionId);

    @Query("select count(b) from BookingOrder b where b.user.id = ?1 and (?2 is null or b.status = ?2) and (?3 is null or b.orderType = ?3)")
    long countByUser_IdAndStatusAndOrderType(UUID userId, @Nullable BookingOrderStatus status, @Nullable OrderType orderType);

    @Query("select b from BookingOrder b where b.status = ?1 and b.startDate < ?2")
    List<BookingOrder> findByStatusAndStartDateBefore(BookingOrderStatus status, Instant startDate);

    @Query("select b from BookingOrder b where b.sitter.id = ?1 and b.startDate = ?2 and b.status in ?3")
    List<BookingOrder> findBySitter_IdAndStartDateAndStatusIn(UUID id, Instant startDate, Collection<BookingOrderStatus> statuses);

    @Query("""
            select b from BookingOrder b
            where b.sitter.id = ?1 and (b.startDate <= ?3 and ?2 <= b.endDate) and b.status in ?4
            """)
    List<BookingOrder> findBySitter_IdAndStartDateAndEndDateAndStatusIn(UUID id, Instant startDate, Instant endDate, Collection<BookingOrderStatus> statuses);

    @Query("SELECT b FROM BookingOrder b " +
           "WHERE b.sitter.id = :sitterId " +
           "AND b.startDate <= :endDate " +
           "AND b.endDate >= :startDate " +
           "AND b.status IN :statuses")
    List<BookingOrder> findBySitterIdAndDateRange(
            @Param("sitterId") UUID sitterId,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Collection<BookingOrderStatus> statuses
    );

}