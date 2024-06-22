package com.example.wallet_java.service;

import com.example.wallet_java.dto.WalletRequest;
import com.example.wallet_java.exception.InsufficientFundsException;
import com.example.wallet_java.exception.WalletNotFoundException;
import com.example.wallet_java.model.OperationType;
import com.example.wallet_java.model.Transaction;
import com.example.wallet_java.model.Wallet;
import com.example.wallet_java.repository.TransactionRepository;
import com.example.wallet_java.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public void updateWallet(WalletRequest request) {
        // Находим кошелек по его идентификатору
        Optional<Wallet> walletOpt = walletRepository.findById(request.getWalletId());
        if (!walletOpt.isPresent()) {
            throw new WalletNotFoundException("Wallet not found");
        }

        Wallet wallet = walletOpt.get();
        BigDecimal newBalance;

        // Обновляем баланс в зависимости от типа операции
        if (request.getOperationType() == OperationType.DEPOSIT) {
            newBalance = wallet.getBalance().add(request.getAmount());
        } else if (request.getOperationType() == OperationType.WITHDRAW) {
            if (wallet.getBalance().compareTo(request.getAmount()) < 0) {
                throw new InsufficientFundsException("Insufficient funds");
            }
            newBalance = wallet.getBalance().subtract(request.getAmount());
        } else {
            throw new IllegalArgumentException("Invalid operation type");
        }

        // Сохраняем обновленный баланс кошелька
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);

        // Создаем и сохраняем запись о транзакции
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setWalletId(wallet.getId());
        transaction.setOperationType(request.getOperationType());
        transaction.setAmount(request.getAmount());
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
    }

    public BigDecimal getWalletBalance(UUID walletId) {
        // Находим кошелек по его идентификатору или выбрасываем исключение, если он не найден
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));
        return wallet.getBalance();
    }
}
