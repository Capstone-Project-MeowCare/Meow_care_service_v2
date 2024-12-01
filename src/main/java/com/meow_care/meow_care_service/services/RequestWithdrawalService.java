package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.withdraw.RequestWithdrawalCreateDto;
import com.meow_care.meow_care_service.dto.withdraw.RequestWithdrawalDto;
import com.meow_care.meow_care_service.entities.RequestWithdrawal;
import com.meow_care.meow_care_service.services.base.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface RequestWithdrawalService extends BaseService<RequestWithdrawalDto, RequestWithdrawal> {
    ResponseEntity<?> getAllDeletedRequests();
    ResponseEntity<?> getAllRequestsByWallet(UUID walletId);
    ResponseEntity<?> createNewRequest(RequestWithdrawalCreateDto request);
    ResponseEntity<?> updateRequest(UUID requestUUID, RequestWithdrawalCreateDto request);
    ResponseEntity<?> completeRequest(UUID requestUUID);
    ResponseEntity<?> cancelRequest(UUID requestUUID);
    ResponseEntity<?> softDeleteRequest(UUID requestUUID);
    ResponseEntity<?> restoreRequest(UUID requestUUID);
}
