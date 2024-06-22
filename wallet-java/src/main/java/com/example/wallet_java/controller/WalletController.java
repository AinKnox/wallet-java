package com.example.wallet_java.controller;

import com.example.wallet_java.dto.WalletRequest;
import com.example.wallet_java.exception.InsufficientFundsException;
import com.example.wallet_java.exception.WalletNotFoundException;
import com.example.wallet_java.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/wallet")
    public ResponseEntity<?> updateWallet(@RequestBody WalletRequest request) {
        try {
            // Вызываем сервис для обновления кошелька с переданным запросом
            walletService.updateWallet(request);
            // Если операция прошла успешно, возвращаем статус 200 OK
            return ResponseEntity.ok().build();
        } catch (WalletNotFoundException e) {
            // Обработка исключения, если кошелек не найден
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet not found");
        } catch (InsufficientFundsException e) {
            // Обработка исключения, если недостаточно средств для операции
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Insufficient funds");
        } catch (Exception e) {
            // Обработка других исключений, включая внутренние серверные ошибки
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    // Метод контроллера для получения баланса кошелька по его идентификатору
    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<?> getWalletBalance(@PathVariable UUID walletId) {
        try {
            // Вызываем сервис для получения баланса кошелька по идентификатору
            return ResponseEntity.ok(walletService.getWalletBalance(walletId));
        } catch (WalletNotFoundException e) {
            // Обработка исключения, если кошелек не найден
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet not found");
        }
    }
}
