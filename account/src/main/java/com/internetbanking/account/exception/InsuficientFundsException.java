package com.internetbanking.account.exception;

public class InsuficientFundsException extends RuntimeException {
    public InsuficientFundsException() {
    }

    public InsuficientFundsException(String message) {
        super(message);
    }
}
