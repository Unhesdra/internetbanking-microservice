package com.internetbanking.account.exception;

public class AccountDoesNotBelongToClientException extends RuntimeException {
    public AccountDoesNotBelongToClientException(String message) {
        super(message);
    }
}
