package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.RequestWithdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RequestWithdrawalRepository extends JpaRepository<RequestWithdrawal, UUID> {
    @Query("select r from RequestWithdrawal r where r.wallet.id = ?1")
    List<RequestWithdrawal> findByWalletId(UUID id);

    @Query("select r from RequestWithdrawal r where r.deleted = ?1")
    List<RequestWithdrawal> findAllByDeleted(boolean b);
}