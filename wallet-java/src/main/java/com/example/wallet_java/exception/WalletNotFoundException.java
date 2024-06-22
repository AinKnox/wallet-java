package com.example.wallet_java.exception;

/**
 * Исключение, выбрасываемое при попытке найти кошелек, который не существует.
 */
public class WalletNotFoundException extends RuntimeException {

    public WalletNotFoundException(String message) {
        super(message);
    }
}
