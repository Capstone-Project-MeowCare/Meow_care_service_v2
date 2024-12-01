package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.withdraw.RequestWithdrawalCreateDto;
import com.meow_care.meow_care_service.services.RequestWithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/request-withdrawal")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class RequestWithdrawalController {

    private final RequestWithdrawalService withdrawalService;

    @GetMapping("/getAllRequests")
    public ResponseEntity<?> getAllRequests() {
        return withdrawalService.getAll();
    }

    @GetMapping("/getAllDeletedRequests")
    public ResponseEntity<?> getAllDeletedRequests() {
        return withdrawalService.getAllDeletedRequests();
    }

    @GetMapping("/getRequestById/{requestUUID}")
    public ResponseEntity<?> getRequestById(@PathVariable("requestUUID") UUID requestUUID) {
        return withdrawalService.get(requestUUID);
    }

    @GetMapping("/getAllRequestsByWallet/{walletUUID}")
    public ResponseEntity<?> getAllRequestsByWallet(@PathVariable("walletUUID") UUID walletUUID) {
        return withdrawalService.getAllRequestsByWallet(walletUUID);
    }

    @PostMapping("/createNewRequest")
    public ResponseEntity<?> createNewRequest(@RequestBody RequestWithdrawalCreateDto request) {
        return withdrawalService.createNewRequest(request);
    }

    @PutMapping("/updateRequest/{requestUUID}")
    public ResponseEntity<?> updateRequest(@PathVariable("requestUUID") UUID requestUUID, @RequestBody RequestWithdrawalCreateDto request) {
        return withdrawalService.updateRequest(requestUUID, request);
    }

    @PutMapping("/completeRequest/{requestUUID}")
    public ResponseEntity<?> completeRequest(@PathVariable("requestUUID") UUID requestUUID) {
        return withdrawalService.completeRequest(requestUUID);
    }

    @PutMapping("/cancelRequest/{requestUUID}")
    public ResponseEntity<?> cancelRequest(@PathVariable("requestUUID") UUID requestUUID) {
        return withdrawalService.cancelRequest(requestUUID);
    }

    @PatchMapping("/softDeleteRequest/{requestUUID}")
    public ResponseEntity<?> softDeleteRequest(@PathVariable("requestUUID") UUID requestUUID) {
        return withdrawalService.softDeleteRequest(requestUUID);
    }

    @PatchMapping("/restoreRequest/{requestUUID}")
    public ResponseEntity<?> restoreRequest(@PathVariable("requestUUID") UUID requestUUID) {
        return withdrawalService.restoreRequest(requestUUID);
    }

}
