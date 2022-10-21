package com.internetbanking.account.exception;

public class ClientHasAccountAtBranchException extends RuntimeException {
    public ClientHasAccountAtBranchException(String message) {
        super(message);
    }
}
