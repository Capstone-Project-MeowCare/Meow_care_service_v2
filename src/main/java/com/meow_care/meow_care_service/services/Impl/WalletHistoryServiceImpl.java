package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.WalletHistoryDto;
import com.meow_care.meow_care_service.entities.Transaction;
import com.meow_care.meow_care_service.entities.WalletHistory;
import com.meow_care.meow_care_service.enums.WalletHistoryType;
import com.meow_care.meow_care_service.mapper.WalletHistoryMapper;
import com.meow_care.meow_care_service.repositories.WalletHistoryRepository;
import com.meow_care.meow_care_service.services.WalletHistoryService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WalletHistoryServiceImpl extends BaseServiceImpl<WalletHistoryDto, WalletHistory, WalletHistoryRepository, WalletHistoryMapper>
        implements WalletHistoryService {
    public WalletHistoryServiceImpl(WalletHistoryRepository repository, WalletHistoryMapper mapper) {
        super(repository, mapper);
    }

    //create wallet history from transaction
    @Override
    public List<WalletHistory> create(Transaction transaction) {
        List<WalletHistory> walletHistories = new ArrayList<>();
        if (transaction.getToUser() != null) {
            WalletHistory receiverWalletHistory = WalletHistory.builder()
                    .wallet(transaction.getReceiverWallet())
                    .transaction(transaction)
                    .amount(transaction.getAmount())
                    .type(WalletHistoryType.RECEIVED)
                    .balance(transaction.getReceiverWallet().getBalance())
                    .build();
            receiverWalletHistory = repository.save(receiverWalletHistory);
            walletHistories.add(receiverWalletHistory);
        }

        if (transaction.getFromUser() != null) {
            WalletHistory senderWalletHistory = WalletHistory.builder()
                    .wallet(transaction.getSenderWallet())
                    .transaction(transaction)
                    .amount(transaction.getAmount().negate())
                    .type(WalletHistoryType.SENT)
                    .balance(transaction.getSenderWallet().getBalance())
                    .build();
            senderWalletHistory = repository.save(senderWalletHistory);
            walletHistories.add(senderWalletHistory);
        }

        return walletHistories;
    }
}
