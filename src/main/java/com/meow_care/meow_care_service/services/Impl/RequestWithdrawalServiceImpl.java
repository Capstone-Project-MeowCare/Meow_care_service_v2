package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.dto.withdraw.RequestWithdrawalCreateDto;
import com.meow_care.meow_care_service.dto.withdraw.RequestWithdrawalDto;
import com.meow_care.meow_care_service.entities.RequestWithdrawal;
import com.meow_care.meow_care_service.entities.Wallet;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.WithdrawStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.RequestWithdrawalMapper;
import com.meow_care.meow_care_service.repositories.RequestWithdrawalRepository;
import com.meow_care.meow_care_service.repositories.WalletRepository;
import com.meow_care.meow_care_service.services.RequestWithdrawalService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RequestWithdrawalServiceImpl extends BaseServiceImpl<RequestWithdrawalDto, RequestWithdrawal, RequestWithdrawalRepository, RequestWithdrawalMapper> implements RequestWithdrawalService {

    private final WalletRepository walletRepository;

    public RequestWithdrawalServiceImpl(RequestWithdrawalRepository repository, RequestWithdrawalMapper mapper, WalletRepository walletRepository) {
        super(repository, mapper);
        this.walletRepository = walletRepository;
    }

    @Override
    public ResponseEntity<?> getAllDeletedRequests() {
        List<RequestWithdrawal> deletedRequests = repository.findAllByDeleted(true);
        return ApiResponse.success(mapper.toDtoList(deletedRequests));
    }

    @Override
    public ResponseEntity<?> getAllRequestsByWallet(UUID walletId) {
        List<RequestWithdrawal> requests = repository.findByWalletId(walletId);
        return ApiResponse.success(mapper.toDtoList(requests));
    }

    @Override
    public ResponseEntity<?> createNewRequest(RequestWithdrawalCreateDto request) {
        Wallet wallet = walletRepository.findByUserId(request.getUserId()).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Wallet not found")
        );

        if (wallet.getBalance().compareTo(request.getBalance()) < 0) {
            throw new ApiException(ApiStatus.AMOUNT_NOT_ENOUGH, "Amount not enough");
        }


        RequestWithdrawal requestWithdrawal = mapper.toEntity(request);
        requestWithdrawal.setProcessStatus(WithdrawStatus.PENDING);
        requestWithdrawal.setDeleted(false);
        requestWithdrawal.setWallet(wallet);
        repository.saveAndFlush(requestWithdrawal);

        wallet.setBalance(wallet.getBalance().subtract(requestWithdrawal.getBalance()));
        walletRepository.save(wallet);

        return ApiResponse.success(mapper.toDto(requestWithdrawal));
    }

    @Override
    public ResponseEntity<?> updateRequest(UUID requestUUID, RequestWithdrawalCreateDto request) {
        return ApiResponse.notImplemented();
    }

    @Override
    public ResponseEntity<?> completeRequest(UUID requestUUID) {
        RequestWithdrawal requestWithdrawal = repository.findById(requestUUID).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Request not found")
        );

        requestWithdrawal.setProcessStatus(WithdrawStatus.PAID);
        repository.saveAndFlush(requestWithdrawal);

        return ApiResponse.success(mapper.toDto(requestWithdrawal));
    }

    @Override
    public ResponseEntity<?> cancelRequest(UUID requestUUID) {
        RequestWithdrawal requestWithdrawal = repository.findById(requestUUID).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Request not found")
        );

        requestWithdrawal.setProcessStatus(WithdrawStatus.PAID_FAIL);
        repository.saveAndFlush(requestWithdrawal);

        Wallet wallet = requestWithdrawal.getWallet();
        wallet.setBalance(wallet.getBalance().add(requestWithdrawal.getBalance()));
        walletRepository.save(wallet);

        return ApiResponse.success(mapper.toDto(requestWithdrawal));
    }

    @Override
    public ResponseEntity<?> softDeleteRequest(UUID requestUUID) {
        RequestWithdrawal requestWithdrawal = repository.findById(requestUUID).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Request not found")
        );

        requestWithdrawal.setDeleted(true);
        repository.saveAndFlush(requestWithdrawal);

        return ApiResponse.deleted();
    }

    @Override
    public ResponseEntity<?> restoreRequest(UUID requestUUID) {
        RequestWithdrawal requestWithdrawal = repository.findById(requestUUID).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Request not found")
        );

        requestWithdrawal.setDeleted(false);
        repository.saveAndFlush(requestWithdrawal);

        return ApiResponse.success();
    }
}
