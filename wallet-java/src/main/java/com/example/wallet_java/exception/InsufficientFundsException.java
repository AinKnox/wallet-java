package com.example.wallet_java.exception;

/**
 * Исключение, выбрасываемое при попытке выполнить операцию с кошельком из-за недостатка средств.
 */
public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String message) {
        super(message);
    }
}
